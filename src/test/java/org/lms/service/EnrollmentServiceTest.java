package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Course;
import org.lms.entity.Enrollment;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.EnrollmentRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private AppUserService userService;

    @Mock
    private CourseService courseService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Course course;
    private Student student;
    private Instructor instructor;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");

        course = new Course();
        course.setId(1L);
        course.setName("Java Programming");
        course.setDescription("Learn Java");
        course.setDuration(12L);
        course.setInstructor(instructor);

        student = new Student();
        student.setId(2L);
        student.setFirstName("Jane");
        student.setLastName("Smith");

        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setCourse(course);
        enrollment.setStudent(student);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(userService.getById(2L)).thenReturn(student);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        when(notificationService.create(anyLong(), anyString(), anyString())).thenReturn(true);

        boolean result = enrollmentService.create(2L, 1L);

        assertTrue(result);
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(notificationService, times(2)).create(anyLong(), anyString(), anyString());
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(userService.getById(2L)).thenReturn(student);
        when(enrollmentRepository.findByStudentAndCourse(any(Student.class), any(Course.class)))
                .thenReturn(enrollment);
        doNothing().when(enrollmentRepository).deleteById(anyLong());

        boolean result = enrollmentService.delete(2L, 1L);

        assertTrue(result);
        verify(enrollmentRepository).deleteById(anyLong());
    }

    @Test
    void checkStudentId_ShouldReturnTrue_WhenEnrollmentExists() {
        when(courseService.getById(1L)).thenReturn(course);
        when(userService.getById(2L)).thenReturn(student);
        when(enrollmentRepository.findByStudentAndCourse(any(Student.class), any(Course.class)))
                .thenReturn(enrollment);

        boolean result = enrollmentService.checkStudentId(2L, 1L);

        assertTrue(result);
        verify(enrollmentRepository).findByStudentAndCourse(any(Student.class), any(Course.class));
    }

    @Test
    void getByCourseId_ShouldReturnListOfStudents_WhenCourseExists() {
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        when(courseService.getById(1L)).thenReturn(course);
        when(enrollmentRepository.findAllByCourseId(1L)).thenReturn(enrollments);

        List<Student> result = enrollmentService.getByCourseId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(student.getId(), result.get(0).getId());
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = enrollmentService.create(2L, 1L);

        assertFalse(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = enrollmentService.delete(2L, 1L);

        assertFalse(result);
    }

    @Test
    void checkStudentId_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = enrollmentService.checkStudentId(2L, 1L);

        assertFalse(result);
    }

    @Test
    void getByCourseId_ShouldReturnNull_WhenCourseDoesNotExist() {
        when(courseService.getById(1L)).thenReturn(null);

        List<Student> result = enrollmentService.getByCourseId(1L);

        assertNull(result);
    }
}