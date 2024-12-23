package org.lms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.controller.LessonRestController;
import org.lms.entity.Course;
import org.lms.entity.Lesson;
import org.lms.entity.UserRole;
import org.lms.service.LessonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonRestControllerTest {

    @InjectMocks
    private LessonRestController lessonRestController;

    @Mock
    private LessonService lessonService;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private EntityMapper entityMapper;

    private Lesson mockLesson;
    private Course mockCourse;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCourse = new Course();
        mockCourse.setId(1L);

        mockLesson = new Lesson();
        mockLesson.setId(1L);
        mockLesson.setCourse(mockCourse);
    }

    @Test
    void testCreateLesson() {
        Long courseId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(true);
        when(lessonService.create(mockLesson, courseId)).thenReturn(true);

        ResponseEntity<String> response = lessonRestController.create(courseId, mockLesson);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All good!", response.getBody());
    }

    @Test
    void testCreateLessonUnauthorized() {
        Long courseId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(false);

        ResponseEntity<String> response = lessonRestController.create(courseId, mockLesson);

        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    void testGetLessonById() {
        Long courseId = 1L;
        Long lessonId = 1L;

        when(authorizationManager.hasAccess(courseId)).thenReturn(true);
        when(lessonService.getById(courseId, lessonId)).thenReturn(mockLesson);
        when(entityMapper.map(mockLesson)).thenReturn(mockLesson.toMap(UserRole.INSTRUCTOR));

        ResponseEntity<Object> response = lessonRestController.getById(courseId, lessonId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllLessons() {
        Long courseId = 1L;
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(mockLesson);

        when(authorizationManager.hasAccess(courseId)).thenReturn(true);
        when(lessonService.getAll(courseId)).thenReturn(lessons);

        ResponseEntity<Object> response = lessonRestController.getAll(courseId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteLesson() {
        Long courseId = 1L;
        Long lessonId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(true);
        when(lessonService.delete(courseId, lessonId)).thenReturn(true);

        ResponseEntity<String> response = lessonRestController.delete(courseId, lessonId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Lesson deleted successfully!", response.getBody());
    }

    @Test
    void testDeleteLessonUnauthorized() {
        Long courseId = 1L;
        Long lessonId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(false);
        ResponseEntity<String> response = lessonRestController.delete(courseId, lessonId);
        assertEquals(403, response.getStatusCodeValue());
    }
}
