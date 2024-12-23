package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Course;
import org.lms.entity.User.Instructor;
import org.lms.repository.CourseRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CourseService courseService;

    private Course course;
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
    }

    @Test
    void existsById_ShouldReturnTrue_WhenCourseExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        assertTrue(courseService.existsById(1L));
        verify(courseRepository).existsById(1L);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(userService.getById(1L)).thenReturn(instructor);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course newCourse = new Course();
        newCourse.setName("New Course");

        assertTrue(courseService.create(newCourse, 1L));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course updatedCourse = new Course();
        updatedCourse.setName("Updated Course");
        updatedCourse.setDescription("Updated Description");

        assertTrue(courseService.update(1L, updatedCourse));
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        doNothing().when(courseRepository).deleteById(1L);

        assertTrue(courseService.delete(1L));
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void getById_ShouldReturnCourse_WhenExists() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = courseService.getById(1L);
        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        verify(courseRepository).findById(1L);
    }

    @Test
    void getAll_ShouldReturnListOfCourses() {
        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseRepository).findAll();
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(userService.getById(1L)).thenThrow(new RuntimeException());

        Course newCourse = new Course();
        assertFalse(courseService.create(newCourse, 1L));
    }

    @Test
    void update_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseRepository.findById(1L)).thenThrow(new RuntimeException());

        Course updatedCourse = new Course();
        assertFalse(courseService.update(1L, updatedCourse));
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(courseRepository).deleteById(1L);

        assertFalse(courseService.delete(1L));
    }
}