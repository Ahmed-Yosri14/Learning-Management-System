package org.lms.service;

import org.lms.entity.AnswerFormat;
import org.lms.entity.Course;
import org.lms.entity.Question;
import org.lms.entity.Quiz;
import org.lms.repository.AnswerFormatRepository;
import org.lms.repository.QuestionRepository;
import org.lms.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private AnswerFormatRepository answerFormatRepository;
    public boolean create(Long courseId, Long quizId, Question question) {
        try {
            if (courseService.getById(courseId) == null) {
                System.out.println("Course not found");
                return false;
            }

            Quiz quiz = quizService.getById(courseId, quizId);
            if (quiz == null) {
                System.out.println("Quiz not found");
                return false;
            }

            question.setQuiz(quiz);

            if (question.getAnswerFormat() != null) {
                AnswerFormat savedAnswerFormat = answerFormatRepository.save(question.getAnswerFormat());
                question.setAnswerFormat(savedAnswerFormat);
            }

            questionRepository.save(question);

            System.out.println("Question successfully created!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("An error occurred while creating the question.");
        return false;
    }


    public boolean update(Long courseId, Long quizId, Long questionId, Question updatedQuestion) {
        try {
            Course course = courseService.getById(courseId);
            if (course == null) {
                System.out.println("Course not found.");
                return false;
            }

            Quiz quiz = quizService.getById(courseId, quizId);
            if (quiz == null || !quiz.getCourse().getId().equals(course.getId())) {
                System.out.println("Quiz not found or does not belong to the specified course.");
                return false;
            }

            Question existingQuestion = questionRepository.findById(questionId).orElse(null);
            if (existingQuestion == null || !existingQuestion.getQuiz().getId().equals(quizId)) {
                System.out.println("Question not found or does not belong to the specified quiz.");
                return false;
            }

            if (updatedQuestion.getQuestionStatement() != null) {
                existingQuestion.setQuestionStatement(updatedQuestion.getQuestionStatement());
            }

            if (updatedQuestion.getAnswerFormat() != null) {
                AnswerFormat updatedAnswerFormat = updatedQuestion.getAnswerFormat();
                updatedAnswerFormat.setId(existingQuestion.getAnswerFormat().getId());
                existingQuestion.setAnswerFormat(updatedAnswerFormat);
            }

            questionRepository.save(existingQuestion);

            System.out.println("Question successfully updated!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while updating the question: " + e.getMessage());
            return false;
        }
    }

    public Question getById(Long courseId,Long quizId,Long id)
    {
        try {
            Question question = questionRepository.findById(id).get();
            if (question.getQuiz() != null && question.getQuiz().getId().equals(quizId)&&question.getQuiz().getCourse().getId().equals(courseId)) {
                return question;
            } else {
                System.out.println("Question not found or does not belong to the specified course.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving question: " + e.getMessage());
            return null;
        }
    }
    public List<Question> getAll(Long courseId,Long quizId){
        try {
            Course course = courseService.getById(courseId);
            if(course == null){
                return null;
            }
            return questionRepository.findByQuizId(quizId);
        } catch (Exception e) {
            System.out.println("Error fetching question for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    public boolean delete(Long courseId, Long quizId,Long id) {
        try {
            if (courseService.getById(courseId)==null || quizService.getById(courseId,quizId)==null|| !Objects.equals(courseService.getById(courseId).getId(), quizService.getById(courseId, quizId).getId())) {
                return false;
            }
            Question question = questionRepository.findById(id).orElse(null);
            if (question == null || !question.getQuiz().getId().equals(quizId)) {
                return false;
            }
            questionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

