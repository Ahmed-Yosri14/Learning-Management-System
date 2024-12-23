package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Answer.MCQAnswer;
import org.lms.entity.Course;
import org.lms.entity.Question;
import org.lms.entity.User.Instructor;
import org.lms.repository.AnswerFormatRepository;
import org.lms.repository.QuestionRepository;
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
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private AnswerFormatRepository answerFormatRepository;

    @InjectMocks
    private QuestionService questionService;

    private Course course;
    private Question question;
    private MCQAnswer mcqAnswer;
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

        mcqAnswer = new MCQAnswer();
        mcqAnswer.setId(1L);
        mcqAnswer.setCorrectAnswer("A");
        mcqAnswer.setOptions(new String[]{"A", "B", "C", "D"});

        question = new Question();
        question.setId(1L);
        question.setQuestionStatement("What is Java?");
        question.setAnswerFormat(mcqAnswer);
        question.setCourse(course);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(answerFormatRepository.save(any())).thenReturn(mcqAnswer);
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        boolean result = questionService.create(1L, question);

        assertTrue(result);
        verify(questionRepository).save(any(Question.class));
        verify(answerFormatRepository).save(any());
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question updatedQuestion = new Question();
        updatedQuestion.setQuestionStatement("Updated question");
        updatedQuestion.setAnswerFormat(mcqAnswer);

        boolean result = questionService.update(1L, 1L, updatedQuestion);

        assertTrue(result);
        verify(questionRepository).save(any(Question.class));
    }

    @Test
    void getById_ShouldReturnQuestion_WhenExists() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        Question result = questionService.getById(1L, 1L);

        assertNotNull(result);
        assertEquals(question.getId(), result.getId());
    }

    @Test
    void getAll_ShouldReturnListOfQuestions() {
        List<Question> questions = Arrays.asList(question);
        when(courseService.getById(1L)).thenReturn(course);
        when(questionRepository.findAllByCourseId(1L)).thenReturn(questions);

        List<Question> result = questionService.getAll(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.getById(1L)).thenReturn(course);
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        doNothing().when(questionRepository).deleteById(1L);

        boolean result = questionService.delete(1L, 1L);

        assertTrue(result);
        verify(questionRepository).deleteById(1L);
    }

    @Test
    void create_ShouldReturnFalse_WhenCourseNotFound() {
        when(courseService.getById(1L)).thenReturn(null);

        boolean result = questionService.create(1L, question);

        assertFalse(result);
        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void update_ShouldReturnFalse_WhenQuestionNotFound() {
        when(courseService.getById(1L)).thenReturn(course);
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        Question updatedQuestion = new Question();
        updatedQuestion.setQuestionStatement("Updated question");
        updatedQuestion.setAnswerFormat(mcqAnswer);

        boolean result = questionService.update(1L, 1L, updatedQuestion);

        assertFalse(result);
        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void getById_ShouldReturnNull_WhenQuestionNotFound() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        Question result = questionService.getById(1L, 1L);

        assertNull(result);
    }

    @Test
    void getAll_ShouldReturnNull_WhenCourseNotFound() {
        when(courseService.getById(1L)).thenReturn(null);

        List<Question> result = questionService.getAll(1L);

        assertNull(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenQuestionNotFound() {
        when(courseService.getById(1L)).thenReturn(course);
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = questionService.delete(1L, 1L);

        assertFalse(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = questionService.create(1L, question);

        assertFalse(result);
    }

    @Test
    void update_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = questionService.update(1L, 1L, question);

        assertFalse(result);
    }
}