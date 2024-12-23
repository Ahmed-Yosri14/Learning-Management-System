package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Course;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.AssignmentFeedbackRepository;
import org.lms.repository.FeedbackRepository;
import org.lms.service.Feedback.AssignmentFeedbackService;
import org.lms.service.Submission.AssignmentSubmissionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentFeedbackServiceTest {

    @Mock
    private AssignmentFeedbackRepository assignmentFeedbackRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private AssignmentSubmissionService assignmentSubmissionService;

    @InjectMocks
    private AssignmentFeedbackService assignmentFeedbackService;

    private Course course;
    private Assignment assignment;
    private Student student;
    private Instructor instructor;
    private AssignmentSubmission submission;
    private AssignmentFeedback feedback;

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
        student.setId(2L);
        student.setFirstName("Jane");
        student.setLastName("Smith");

        submission = new AssignmentSubmission();
        submission.setId(1L);
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setFilePath("/path/to/file.pdf");

        feedback = new AssignmentFeedback();
        feedback.setId(1L);
        feedback.setGrade(85.0);
        feedback.setMaxGrade(100.0);
        feedback.setComment("Good work!");
        feedback.setAssignmentSubmission(submission);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(feedbackRepository.existsById(1L)).thenReturn(true);
        when(assignmentSubmissionService.existsById(1L, 1L, 1L)).thenReturn(true);
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        boolean result = assignmentFeedbackService.existsById(1L, 1L, 1L, 1L);

        assertTrue(result);
    }

    @Test
    void getById_ShouldReturnFeedback_WhenExists() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        AssignmentFeedback result = assignmentFeedbackService.getById(1L);

        assertNotNull(result);
        assertEquals(feedback.getId(), result.getId());
    }

    @Test
    void getAllByAssignmentSubmissionId_ShouldReturnList_WhenExists() {
        List<AssignmentFeedback> feedbacks = Arrays.asList(feedback);
        when(assignmentFeedbackRepository.findAllByAssignmentSubmissionId(1L)).thenReturn(feedbacks);

        List<AssignmentFeedback> result = assignmentFeedbackService.getAllByAssignmentSubmissionId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnFalse_WhenSubmissionNotFound() {
        when(assignmentSubmissionService.getById(1L)).thenReturn(null);

        boolean result = assignmentFeedbackService.create(1L, feedback);

        assertFalse(result);
        verify(assignmentFeedbackRepository, never()).save(any(AssignmentFeedback.class));
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));
        when(feedbackRepository.save(any(AssignmentFeedback.class))).thenReturn(feedback);

        AssignmentFeedback updatedFeedback = new AssignmentFeedback();
        updatedFeedback.setGrade(90.0);
        updatedFeedback.setComment("Excellent work!");

        boolean result = assignmentFeedbackService.update(1L, updatedFeedback);

        assertTrue(result);
        verify(feedbackRepository).save(any(AssignmentFeedback.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        doNothing().when(feedbackRepository).deleteById(1L);

        boolean result = assignmentFeedbackService.delete(1L);

        assertTrue(result);
        verify(feedbackRepository).deleteById(1L);
    }

    @Test
    void getAllByAssignmentSubmissionId_ShouldReturnNull_WhenExceptionOccurs() {
        when(assignmentFeedbackRepository.findAllByAssignmentSubmissionId(1L))
                .thenThrow(new RuntimeException());

        List<AssignmentFeedback> result = assignmentFeedbackService.getAllByAssignmentSubmissionId(1L);

        assertNull(result);
    }

    @Test
    void update_ShouldReturnFalse_WhenFeedbackNotFound() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = assignmentFeedbackService.update(1L, feedback);

        assertFalse(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(assignmentSubmissionService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = assignmentFeedbackService.create(1L, feedback);

        assertFalse(result);
        verify(assignmentFeedbackRepository, never()).save(any(AssignmentFeedback.class));
    }

    @Test
    void getById_ShouldReturnNull_WhenExceptionOccurs() {
        when(feedbackRepository.findById(1L)).thenThrow(new RuntimeException());

        AssignmentFeedback result = assignmentFeedbackService.getById(1L);

        assertNull(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(feedbackRepository).deleteById(1L);

        boolean result = assignmentFeedbackService.delete(1L);

        assertFalse(result);
    }
}