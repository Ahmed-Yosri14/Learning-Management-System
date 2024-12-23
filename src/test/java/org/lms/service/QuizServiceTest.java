package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.AuthorizationManager;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Assessment.StudentQuiz;
import org.lms.entity.Course;
import org.lms.entity.Question;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.repository.AssessmentRepository;
import org.lms.repository.QuizRepository;
import org.lms.repository.StudentQuizRepository;
import org.lms.service.Assessment.QuizService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private StudentQuizRepository studentQuizRepository;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private CourseService courseService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AssessmentRepository assessmentRepository;

    @InjectMocks
    private QuizService quizService;

    private Course course;
    private Quiz quiz;
    private Question question;
    private Student student;
    private Instructor instructor;
    private StudentQuiz studentQuiz;

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
        quiz.setTitle("Java Basics Quiz");
        quiz.setDescription("Test your Java knowledge");
        quiz.setDuration(60L);
        quiz.setCourse(course);
        quiz.setStartDate(LocalDateTime.now());
        quiz.setQuestionNum(5.0);
        quiz.setQuestions(new ArrayList<>());

        question = new Question();
        question.setId(1L);
        question.setQuestionStatement("What is Java?");
        question.setCourse(course);

        student = new Student();
        student.setId(2L);
        student.setFirstName("Jane");
        student.setLastName("Smith");

        studentQuiz = new StudentQuiz();
        studentQuiz.setId(1L);
        studentQuiz.setStudent(student);
        studentQuiz.setQuiz(quiz);
        studentQuiz.setQuestions(Arrays.asList(question));

        ReflectionTestUtils.setField(quizService, "assessmentRepository", assessmentRepository);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.save(any(Quiz.class))).thenReturn(quiz);

        boolean result = quizService.create(1L, quiz);

        assertTrue(result);
        verify(assessmentRepository).save(any(Quiz.class));
        verify(notificationService).createToAllEnrolled(anyLong(), anyString(), anyString());
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.existsById(1L)).thenReturn(true);
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(assessmentRepository.save(any(Quiz.class))).thenReturn(quiz);

        Quiz updatedQuiz = new Quiz();
        updatedQuiz.setTitle("Updated Quiz");
        updatedQuiz.setDescription("Updated Description");

        boolean result = quizService.update(1L, 1L, updatedQuiz);

        assertTrue(result);
        verify(assessmentRepository).save(any(Quiz.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.existsById(1L)).thenReturn(true);
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(quiz));
        doNothing().when(assessmentRepository).deleteById(1L);

        boolean result = quizService.delete(1L, 1L);

        assertTrue(result);
        verify(assessmentRepository).deleteById(1L);
    }

    @Test
    void getById_ShouldReturnQuiz_WhenExists() {
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Quiz result = quizService.getById(1L, 1L);

        assertNotNull(result);
        assertEquals(quiz.getId(), result.getId());
    }

    @Test
    void getAll_ShouldReturnListOfQuizzes() {
        List<Quiz> quizzes = Arrays.asList(quiz);
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(quizRepository.findAllByCourse(course)).thenReturn(quizzes);

        List<Quiz> result = quizService.getAll(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void removeQuestionsFromQuiz_ShouldReturnTrue_WhenSuccessful() {
        quiz.setQuestions(new ArrayList<>(Arrays.asList(question)));
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionService.getById(1L, 1L)).thenReturn(question);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        boolean result = quizService.removeQuestionsFromQuiz(1L, 1L, Arrays.asList(1L));

        assertTrue(result);
        verify(quizRepository).save(any(Quiz.class));
    }

    @Test
    void addQuestionsToQuiz_ShouldReturnTrue_WhenSuccessful() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionService.getById(1L, 1L)).thenReturn(question);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        boolean result = quizService.addQuestionstoQuiz(1L, 1L, Arrays.asList(1L));

        assertTrue(result);
        verify(quizRepository).save(any(Quiz.class));
    }

    @Test
    void getQuestionsForStudent_ShouldReturnQuestions_WhenExistingInstance() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(authorizationManager.getCurrentUser()).thenReturn(student);
        when(studentQuizRepository.findByStudentIdAndQuizId(2L, 1L))
                .thenReturn(Optional.of(studentQuiz));

        List<Question> result = quizService.getQuestionsForStudent(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getQuestionsForStudent_ShouldCreateNewInstance_WhenNoExistingInstance() {
        quiz.setQuestions(Arrays.asList(question, question, question, question, question));
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(authorizationManager.getCurrentUser()).thenReturn(student);
        when(studentQuizRepository.findByStudentIdAndQuizId(2L, 1L))
                .thenReturn(Optional.empty());
        when(studentQuizRepository.save(any(StudentQuiz.class))).thenReturn(studentQuiz);

        List<Question> result = quizService.getQuestionsForStudent(1L, 1L);

        assertNotNull(result);
        verify(studentQuizRepository).save(any(StudentQuiz.class));
    }

    @Test
    void getQuestionsForStudent_ShouldReturnNull_WhenQuizNotFound() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        List<Question> result = quizService.getQuestionsForStudent(1L, 1L);

        assertNull(result);
    }

    @Test
    void getQuestionsForStudent_ShouldThrowException_WhenNotEnoughQuestions() {
        quiz.setQuestions(Arrays.asList(question));
        quiz.setQuestionNum(5.0);
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(authorizationManager.getCurrentUser()).thenReturn(student);
        when(studentQuizRepository.findByStudentIdAndQuizId(2L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                quizService.getQuestionsForStudent(1L, 1L));
    }

    @Test
    void create_ShouldReturnFalse_WhenCourseDoesNotExist() {
        when(courseService.existsById(1L)).thenReturn(false);

        boolean result = quizService.create(1L, quiz);

        assertFalse(result);
        verify(assessmentRepository, never()).save(any(Quiz.class));
    }

    @Test
    void getAll_ShouldReturnNull_WhenCourseDoesNotExist() {
        when(courseService.existsById(1L)).thenReturn(false);

        List<Quiz> result = quizService.getAll(1L);

        assertNull(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.existsById(1L)).thenThrow(new RuntimeException());

        boolean result = quizService.create(1L, quiz);

        assertFalse(result);
    }
}