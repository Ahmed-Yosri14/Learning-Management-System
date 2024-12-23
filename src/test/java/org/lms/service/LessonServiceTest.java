package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Course;
import org.lms.entity.Lesson;
import org.lms.entity.User.Instructor;
import org.lms.repository.LessonRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LessonService lessonService;

    private Course course;
    private Lesson lesson;
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

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Test Lesson");
        lesson.setDescription("Test Description");
        lesson.setDuration(60L);
        lesson.setCourse(course);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenLessonExists() {
        when(courseService.getById(1L)).thenReturn(course);
        when(courseService.existsById(1L)).thenReturn(true);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        assertTrue(lessonService.existsById(1L, 1L));
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(courseService.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        Lesson updatedLesson = new Lesson();
        updatedLesson.setTitle("Updated Title");

        assertTrue(lessonService.update(1L, updatedLesson, 1L));
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(courseService.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        doNothing().when(lessonRepository).deleteById(1L);

        assertTrue(lessonService.delete(1L, 1L));
        verify(lessonRepository).deleteById(1L);
    }

    @Test
    void getById_WithCourseId_ShouldReturnLesson_WhenExists() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(courseService.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        Lesson result = lessonService.getById(1L, 1L);
        assertNotNull(result);
        assertEquals(lesson.getId(), result.getId());
    }

    @Test
    void getById_ShouldReturnLesson_WhenExists() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        Lesson result = lessonService.getById(1L);
        assertNotNull(result);
        assertEquals(lesson.getId(), result.getId());
    }

    @Test
    void getAll_ShouldReturnListOfLessons() {
        List<Lesson> lessons = Arrays.asList(lesson);
        when(lessonRepository.findAllByCourseId(1L)).thenReturn(lessons);

        List<Lesson> result = lessonService.getAll(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        assertFalse(lessonService.create(new Lesson(), 1L));
    }

    @Test
    void update_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        assertFalse(lessonService.update(1L, new Lesson(), 1L));
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        assertFalse(lessonService.delete(1L, 1L));
    }

    @Test
    void getAll_ShouldReturnNull_WhenExceptionOccurs() {
        when(lessonRepository.findAllByCourseId(1L)).thenThrow(new RuntimeException());

        assertNull(lessonService.getAll(1L));
    }

    @Test
    void generateOtp_ShouldReturnNull_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        assertNull(lessonService.generateOtp(1L, 1L));
    }

    @Test
    void generateOtp_ShouldReturnOtp_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(courseService.existsById(anyLong())).thenReturn(true);
        when(lessonRepository.existsById(anyLong())).thenReturn(true);
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        when(lessonRepository.existsByOtp(anyString())).thenReturn(false);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        String otp = lessonService.generateOtp(1L, 1L);

        assertNotNull(otp);
        assertEquals(6, otp.length());
        verify(lessonRepository).save(any(Lesson.class));
    }
}