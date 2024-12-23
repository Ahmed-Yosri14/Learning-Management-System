package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Answer.MCQAnswer;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Course;
import org.lms.entity.Question;
import org.lms.entity.QuestionAnswer;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.QuizSubmissionRepository;
import org.lms.service.Assessment.QuizService;
import org.lms.service.Feedback.QuizFeedbackService;
import org.lms.service.Submission.QuizSubmissionService;
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
class QuizSubmissionServiceTest {

    @Mock
    private QuizSubmissionRepository quizSubmissionRepository;

    @Mock
    private QuizService quizService;

    @Mock
    private AppUserService appUserService;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuizFeedbackService quizFeedbackService;

    @InjectMocks
    private QuizSubmissionService quizSubmissionService;

    private Course course;
    private Quiz quiz;
    private Student student;
    private Instructor instructor;
    private Question question;
    private QuizSubmission quizSubmission;
    private QuestionAnswer questionAnswer;

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
        quiz.setQuestionNum(1.0);

        student = new Student();
        student.setId(1L);
        student.setFirstName("Jane");
        student.setLastName("Smith");

        MCQAnswer mcqAnswer = new MCQAnswer();
        mcqAnswer.setCorrectAnswer("A");
        mcqAnswer.setOptions(new String[]{"A", "B", "C"});

        question = new Question();
        question.setId(1L);
        question.setQuestionStatement("What is Java?");
        question.setCourse(course);
        question.setAnswerFormat(mcqAnswer);

        questionAnswer = new QuestionAnswer();
        questionAnswer.setQuestionId(1L);
        questionAnswer.setAnswer("A");

        quizSubmission = new QuizSubmission();
        quizSubmission.setId(1L);
        quizSubmission.setStudent(student);
        quizSubmission.setQuiz(quiz);
        quizSubmission.setQuestionsAnswer(Arrays.asList(questionAnswer));
    }

    @Test
    void getById_ShouldReturnSubmission_WhenExists() {
        when(quizSubmissionRepository.findById(1L)).thenReturn(Optional.of(quizSubmission));

        QuizSubmission result = quizSubmissionService.getById(1L);

        assertNotNull(result);
        assertEquals(quizSubmission.getId(), result.getId());
    }

    @Test
    void getSubmition_ShouldReturnSubmission_WhenExists() {
        when(quizService.getById(1L, 1L)).thenReturn(quiz);
        when(appUserService.getById(1L)).thenReturn(student);
        when(quizSubmissionRepository.findByQuizAndStudent(quiz, student)).thenReturn(quizSubmission);

        QuizSubmission result = quizSubmissionService.getSubmition(1L, 1L, 1L);

        assertNotNull(result);
        assertEquals(quizSubmission.getId(), result.getId());
    }

    @Test
    void getAllSubmitions_ShouldReturnList_WhenExists() {
        List<QuizSubmission> submissions = Arrays.asList(quizSubmission);
        when(quizService.getById(1L, 1L)).thenReturn(quiz);
        when(quizSubmissionRepository.findByQuiz(quiz)).thenReturn(submissions);

        List<QuizSubmission> result = quizSubmissionService.getAllSubmitions(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(quizService.getById(1L, 1L)).thenReturn(quiz);
        when(appUserService.getById(1L)).thenReturn(student);
        when(quizSubmissionRepository.save(any(QuizSubmission.class))).thenReturn(quizSubmission);

        boolean result = quizSubmissionService.create(1L, 1L, 1L, quizSubmission);

        assertTrue(result);
        verify(quizSubmissionRepository).save(any(QuizSubmission.class));
    }

    @Test
    void grading_ShouldReturnScore_WhenCorrect() {
        when(questionService.getById(1L, 1L)).thenReturn(question);
        when(quizFeedbackService.create(any())).thenReturn(true);

        Double result = quizSubmissionService.grading(quizSubmission, 1L, 1L, 1L);

        assertNotNull(result);
        assertEquals(1.0, result);
    }

    @Test
    void existsByStudentIdAndQuizId_ShouldReturnTrue_WhenExists() {
        when(quizSubmissionRepository.existsByStudentIdAndQuizId(1L, 1L)).thenReturn(true);

        boolean result = quizSubmissionService.existsByStudentIdAndQuizId(1L, 1L);

        assertTrue(result);
    }

    @Test
    void getSubmition_ShouldReturnNull_WhenQuizNotFound() {
        when(quizService.getById(1L, 1L)).thenReturn(null);

        QuizSubmission result = quizSubmissionService.getSubmition(1L, 1L, 1L);

        assertNull(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenQuizNotFound() {
        when(quizService.getById(1L, 1L)).thenReturn(null);

        boolean result = quizSubmissionService.create(1L, 1L, 1L, quizSubmission);

        assertFalse(result);
    }

    @Test
    void grading_ShouldReturnNull_WhenStudentIdMismatch() {
        Double result = quizSubmissionService.grading(quizSubmission, 1L, 2L, 1L);

        assertNull(result);
    }

    @Test
    void getAllSubmitions_ShouldReturnNull_WhenQuizNotFound() {
        when(quizService.getById(1L, 1L)).thenReturn(null);

        List<QuizSubmission> result = quizSubmissionService.getAllSubmitions(1L, 1L);

        assertNull(result);
    }
}