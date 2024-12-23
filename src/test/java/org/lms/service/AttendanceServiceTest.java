package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.*;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.AttendanceRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private AppUserService appUserService;

    @Mock
    private LessonService lessonService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private AttendanceService attendanceService;

    private Course course;
    private Lesson lesson;
    private Student student;
    private Attendance attendance;
    private Instructor instructor;

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

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Java Basics");
        lesson.setDescription("Introduction to Java");
        lesson.setDuration(60L);
        lesson.setCourse(course);
        lesson.setOtp("123456");

        attendance = new Attendance();
        attendance.setId(1L);
        attendance.setLesson(lesson);
        attendance.setStudent(student);
    }

    @Test
    void tryRecord_ShouldReturnTrue_WhenOtpMatches() {
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(lessonService.existsById(1L, 1L)).thenReturn(true);
        when(appUserService.getById(2L)).thenReturn(student);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        boolean result = attendanceService.tryRecord(2L, 1L, 1L, "123456");

        assertTrue(result);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(lessonService.existsById(1L, 1L)).thenReturn(true);
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(appUserService.getById(2L)).thenReturn(student);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        boolean result = attendanceService.create(2L, 1L, 1L);

        assertTrue(result);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(lessonService.existsById(1L, 1L)).thenReturn(true);
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(appUserService.getById(2L)).thenReturn(student);
        when(attendanceRepository.findByStudentAndLesson(any(Student.class), any(Lesson.class)))
                .thenReturn(attendance);
        doNothing().when(attendanceRepository).deleteById(anyLong());

        boolean result = attendanceService.delete(2L, 1L, 1L);

        assertTrue(result);
        verify(attendanceRepository).deleteById(anyLong());
    }

    @Test
    void checkStudentId_ShouldReturnTrue_WhenAttendanceExists() {
        when(lessonService.existsById(1L, 1L)).thenReturn(true);
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(appUserService.getById(2L)).thenReturn(student);
        when(attendanceRepository.findByStudentAndLesson(any(Student.class), any(Lesson.class)))
                .thenReturn(attendance);

        boolean result = attendanceService.checkStudentId(2L, 1L, 1L);

        assertTrue(result);
        verify(attendanceRepository).findByStudentAndLesson(any(Student.class), any(Lesson.class));
    }

    @Test
    void getByLessonId_ShouldReturnListOfStudents() {
        List<Attendance> attendances = Arrays.asList(attendance);
        when(lessonService.existsById(1L, 1L)).thenReturn(true);
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(attendanceRepository.findAllByLesson(any(Lesson.class))).thenReturn(attendances);

        List<Student> result = attendanceService.getByLessonId(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(student.getId(), result.get(0).getId());
    }

    @Test
    void tryRecord_ShouldReturnFalse_WhenOtpDoesNotMatch() {
        when(lessonService.getById(1L)).thenReturn(lesson);

        boolean result = attendanceService.tryRecord(2L, 1L, 1L, "wrong-otp");

        assertFalse(result);
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(lessonService.existsById(1L, 1L)).thenThrow(new RuntimeException());

        boolean result = attendanceService.create(2L, 1L, 1L);

        assertFalse(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        when(lessonService.existsById(1L, 1L)).thenThrow(new RuntimeException());

        boolean result = attendanceService.delete(2L, 1L, 1L);

        assertFalse(result);
    }

    @Test
    void checkStudentId_ShouldReturnFalse_WhenExceptionOccurs() {
        when(lessonService.existsById(1L, 1L)).thenThrow(new RuntimeException());

        boolean result = attendanceService.checkStudentId(2L, 1L, 1L);

        assertFalse(result);
    }
}