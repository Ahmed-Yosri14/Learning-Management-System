package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Course;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.FeedbackRepository;
import org.lms.repository.QuizFeedbackRepository;
import org.lms.service.Feedback.QuizFeedbackService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizFeedbackServiceTest {

    @Mock
    private QuizFeedbackRepository quizFeedbackRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private QuizFeedbackService quizFeedbackService;

    private Course course;
    private Quiz quiz;
    private Student student;
    private Instructor instructor;
    private QuizSubmission submission;
    private QuizFeedback feedback;

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

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Java Quiz");
        quiz.setDescription("Test your Java knowledge");
        quiz.setDuration(60L);
        quiz.setCourse(course);
        quiz.setStartDate(LocalDateTime.now());

        student = new Student();
        student.setId(2L);
        student.setFirstName("Jane");
        student.setLastName("Smith");

        submission = new QuizSubmission();
        submission.setId(1L);
        submission.setStudent(student);
        submission.setQuiz(quiz);

        feedback = new QuizFeedback();
        feedback.setId(1L);
        feedback.setGrade(85.0);
        feedback.setMaxGrade(100.0);
        feedback.setComment("Good attempt!");
        feedback.setQuizSubmission(submission);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(feedbackRepository.existsById(1L)).thenReturn(true);
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        boolean result = quizFeedbackService.existsById(1L, 1L, 1L, 1L);

        assertTrue(result);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenFeedbackNotFound() {
        when(feedbackRepository.existsById(1L)).thenReturn(false);

        boolean result = quizFeedbackService.existsById(1L, 1L, 1L, 1L);

        assertFalse(result);
    }

    @Test
    void existsById_ShouldReturnFalse_WhenSubmissionIdDoesNotMatch() {
        when(feedbackRepository.existsById(1L)).thenReturn(true);
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        boolean result = quizFeedbackService.existsById(1L, 1L, 2L, 1L);

        assertFalse(result);
    }

    @Test
    void getById_ShouldReturnFeedback_WhenExists() {
        when(feedbackRepository.existsById(1L)).thenReturn(true);
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        QuizFeedback result = quizFeedbackService.getById(1L, 1L, 1L, 1L);

        assertNotNull(result);
        assertEquals(feedback.getId(), result.getId());
    }

    @Test
    void getById_ShouldReturnNull_WhenNotFound() {
        when(feedbackRepository.existsById(1L)).thenReturn(false);

        QuizFeedback result = quizFeedbackService.getById(1L, 1L, 1L, 1L);

        assertNull(result);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(quizFeedbackRepository.save(any(QuizFeedback.class))).thenReturn(feedback);

        boolean result = quizFeedbackService.create(feedback);

        assertTrue(result);
        verify(quizFeedbackRepository).save(any(QuizFeedback.class));
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(quizFeedbackRepository.save(any(QuizFeedback.class))).thenThrow(new RuntimeException());

        boolean result = quizFeedbackService.create(feedback);

        assertFalse(result);
    }

    @Test
    void getAllByQuizSubmissionId_ShouldReturnList_WhenExists() {
        List<QuizFeedback> feedbacks = Arrays.asList(feedback);
        when(quizFeedbackRepository.findAllByQuizSubmissionId(1L)).thenReturn(feedbacks);

        List<QuizFeedback> result = quizFeedbackService.getAllByQuizSubmissionId(1L, 1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(feedback, result.get(0));
    }

    @Test
    void getAllByQuizSubmissionId_ShouldReturnNull_WhenExceptionOccurs() {
        when(quizFeedbackRepository.findAllByQuizSubmissionId(1L)).thenThrow(new RuntimeException());

        List<QuizFeedback> result = quizFeedbackService.getAllByQuizSubmissionId(1L, 1L, 1L);

        assertNull(result);
    }
}