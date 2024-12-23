package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Course;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.AssignmentSubmissionRepository;
import org.lms.service.Assessment.AssignmentService;
import org.lms.service.Submission.AssignmentSubmissionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentSubmissionServiceTest {

    @Mock
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AssignmentSubmissionService assignmentSubmissionService;

    private Course course;
    private Assignment assignment;
    private Student student;
    private Instructor instructor;
    private AssignmentSubmission submission;
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

        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Java Assignment");
        assignment.setDescription("Complete exercises");
        assignment.setDuration(60L);
        assignment.setCourse(course);
        assignment.setStartDate(LocalDateTime.now());

        student = new Student();
        student.setId(1L);
        student.setFirstName("Jane");
        student.setLastName("Smith");

        submission = new AssignmentSubmission();
        submission.setId(1L);
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setFilePath("/path/to/file.pdf");

        multipartFile = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "test content".getBytes()
        );
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(assignmentSubmissionRepository.existsById(1L)).thenReturn(true);
        when(assignmentService.existsById(1L, 1L)).thenReturn(true);
        when(assignmentSubmissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        boolean result = assignmentSubmissionService.existsById(1L, 1L, 1L);

        assertTrue(result);
    }

    @Test
    void getById_ShouldReturnSubmission_WhenExists() {
        when(assignmentSubmissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        AssignmentSubmission result = assignmentSubmissionService.getById(1L);

        assertNotNull(result);
        assertEquals(submission.getId(), result.getId());
    }

    @Test
    void getByIdWithCourseAndAssignment_ShouldReturnSubmission_WhenExists() {
        when(assignmentSubmissionRepository.existsById(1L)).thenReturn(true);
        when(assignmentService.existsById(1L, 1L)).thenReturn(true);
        when(assignmentSubmissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        AssignmentSubmission result = assignmentSubmissionService.getById(1L, 1L, 1L);

        assertNotNull(result);
        assertEquals(submission.getId(), result.getId());
    }

    @Test
    void getAllByAssignmentId_ShouldReturnList_WhenExists() {
        List<AssignmentSubmission> submissions = Arrays.asList(submission);
        when(assignmentService.existsById(1L, 1L)).thenReturn(true);
        when(assignmentSubmissionRepository.findAllByAssignmentId(1L)).thenReturn(submissions);

        List<AssignmentSubmission> result = assignmentSubmissionService.getAllByAssignmentId(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() throws IOException {
        when(assignmentService.existsById(1L, 1L)).thenReturn(true);
        when(assignmentService.getById(1L, 1L)).thenReturn(assignment);
        when(appUserService.getById(1L)).thenReturn(student);
        when(fileStorageService.storeFile(any())).thenReturn("/path/to/file.pdf");
        when(assignmentSubmissionRepository.save(any())).thenReturn(submission);

        boolean result = assignmentSubmissionService.create(1L, 1L, 1L, multipartFile);

        assertTrue(result);
        verify(assignmentSubmissionRepository).save(any());
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(assignmentSubmissionRepository.existsById(1L)).thenReturn(true);
        when(assignmentService.existsById(1L, 1L)).thenReturn(true);
        when(assignmentSubmissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        doNothing().when(assignmentSubmissionRepository).deleteById(1L);

        boolean result = assignmentSubmissionService.delete(1L, 1L, 1L);

        assertTrue(result);
        verify(assignmentSubmissionRepository).deleteById(1L);
    }

    @Test
    void create_ShouldReturnFalse_WhenAssignmentDoesNotExist() {
        when(assignmentService.existsById(1L, 1L)).thenReturn(false);

        boolean result = assignmentSubmissionService.create(1L, 1L, 1L, multipartFile);

        assertFalse(result);
        verify(assignmentSubmissionRepository, never()).save(any());
    }

    @Test
    void getAllByAssignmentId_ShouldReturnNull_WhenAssignmentDoesNotExist() {
        when(assignmentService.existsById(1L, 1L)).thenReturn(false);

        List<AssignmentSubmission> result = assignmentSubmissionService.getAllByAssignmentId(1L, 1L);

        assertNull(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenSubmissionDoesNotExist() {
        when(assignmentSubmissionRepository.existsById(1L)).thenReturn(false);
        boolean result = assignmentSubmissionService.delete(1L, 1L, 1L);
        assertFalse(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(assignmentService.existsById(1L, 1L)).thenThrow(new RuntimeException());

        boolean result = assignmentSubmissionService.create(1L, 1L, 1L, multipartFile);

        assertFalse(result);
    }

    @Test
    void getAllByAssignmentId_ShouldReturnNull_WhenExceptionOccurs() {
        when(assignmentService.existsById(1L, 1L)).thenThrow(new RuntimeException());

        List<AssignmentSubmission> result = assignmentSubmissionService.getAllByAssignmentId(1L, 1L);

        assertNull(result);
    }
}