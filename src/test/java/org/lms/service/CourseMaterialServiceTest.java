package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Course;
import org.lms.entity.CourseMaterial;
import org.lms.entity.User.Instructor;
import org.lms.repository.CourseMaterialRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseMaterialServiceTest {

    @Mock
    private CourseMaterialRepository courseMaterialRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private CourseMaterialService courseMaterialService;

    private Course course;
    private CourseMaterial courseMaterial;
    private Instructor instructor;
    private MultipartFile multipartFile;

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

        courseMaterial = new CourseMaterial();
        courseMaterial.setId(1L);
        courseMaterial.setCourse(course);
        courseMaterial.setFileName("test.pdf");
        courseMaterial.setFilePath("/path/to/test.pdf");

        multipartFile = new MockMultipartFile(
                "test.pdf",
                "test.pdf",
                "application/pdf",
                "test content".getBytes()
        );
    }

    @Test
    void getById_ShouldReturnCourseMaterial_WhenExists() {
        when(courseMaterialRepository.findById(1L)).thenReturn(Optional.of(courseMaterial));

        CourseMaterial result = courseMaterialService.getById(1L);

        assertNotNull(result);
        assertEquals(courseMaterial.getId(), result.getId());
    }

    @Test
    void getAllByCourseId_ShouldReturnList_WhenCourseExists() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseMaterialRepository.findAllByCourseId(1L)).thenReturn(Arrays.asList(courseMaterial));

        List<CourseMaterial> result = courseMaterialService.getAllByCourseId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseMaterial.getId(), result.get(0).getId());
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseMaterialRepository.existsById(1L)).thenReturn(true);
        when(courseMaterialRepository.findById(1L)).thenReturn(Optional.of(courseMaterial));

        boolean result = courseMaterialService.existsById(1L, 1L);

        assertTrue(result);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() throws IOException {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(fileStorageService.storeFile(any(MultipartFile.class))).thenReturn("/path/to/file");
        when(courseMaterialRepository.save(any(CourseMaterial.class))).thenReturn(courseMaterial);

        boolean result = courseMaterialService.create(1L, multipartFile);

        assertTrue(result);
        verify(courseMaterialRepository).save(any(CourseMaterial.class));
        verify(notificationService).createToAllEnrolled(anyLong(), anyString(), anyString());
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseMaterialRepository.existsById(1L)).thenReturn(true);
        when(courseMaterialRepository.findById(1L)).thenReturn(Optional.of(courseMaterial));
        doNothing().when(courseMaterialRepository).deleteById(1L);

        boolean result = courseMaterialService.delete(1L, 1L);

        assertTrue(result);
        verify(courseMaterialRepository).deleteById(1L);
    }

    @Test
    void getAllByCourseId_ShouldReturnNull_WhenExceptionOccurs() {
        when(courseService.existsById(1L)).thenThrow(new RuntimeException());

        List<CourseMaterial> result = courseMaterialService.getAllByCourseId(1L);

        assertNull(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.existsById(1L)).thenThrow(new RuntimeException());

        boolean result = courseMaterialService.create(1L, multipartFile);

        assertFalse(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.existsById(1L)).thenThrow(new RuntimeException());

        boolean result = courseMaterialService.delete(1L, 1L);

        assertFalse(result);
    }

    @Test
    void getById_ShouldReturnNull_WhenDoesNotExist() {
        when(courseMaterialRepository.findById(1L)).thenReturn(Optional.empty());

        CourseMaterial result = courseMaterialService.getById(1L);

        assertNull(result);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenDoesNotExist() {
        when(courseService.existsById(1L)).thenReturn(false);

        boolean result = courseMaterialService.existsById(1L, 1L);

        assertFalse(result);
    }
}