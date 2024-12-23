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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

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
        lesson.setTitle("Java Basics");
        lesson.setDescription("Introduction to Java");
        lesson.setDuration(60L);
        lesson.setCourse(course);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        boolean result = lessonService.existsById(1L, 1L);

        assertTrue(result);
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        Lesson updatedLesson = new Lesson();
        updatedLesson.setTitle("Updated Title");
        updatedLesson.setDescription("Updated Description");

        boolean result = lessonService.update(1L, updatedLesson);

        assertTrue(result);
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        doNothing().when(lessonRepository).deleteById(1L);

        boolean result = lessonService.delete(1L, 1L);

        assertTrue(result);
        verify(lessonRepository).deleteById(1L);
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
        when(lessonRepository.findAll()).thenReturn(lessons);

        List<Lesson> result = lessonService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnFalse_WhenCourseNotFound() {
        when(courseService.getById(1L)).thenReturn(null);

        boolean result = lessonService.create(lesson, 1L);

        assertFalse(result);
        verify(lessonRepository, never()).save(any(Lesson.class));
    }

    @Test
    void update_ShouldReturnFalse_WhenLessonNotFound() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = lessonService.update(1L, lesson);

        assertFalse(result);
        verify(lessonRepository, never()).save(any(Lesson.class));
    }

    @Test
    void delete_ShouldReturnFalse_WhenLessonNotFound() {
        when(courseService.getById(1L)).thenReturn(course);
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = lessonService.delete(1L, 1L);

        assertFalse(result);
        verify(lessonRepository, never()).deleteById(anyLong());
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = lessonService.create(lesson, 1L);

        assertFalse(result);
    }
}