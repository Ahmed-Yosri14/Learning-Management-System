package org.lms.service;

import org.lms.entity.Answer.AnswerFormat;
import org.lms.entity.Course;
import org.lms.entity.Question;
import org.lms.repository.AnswerFormatRepository;
import org.lms.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseService courseService;


    @Autowired
    private AnswerFormatRepository answerFormatRepository;

    public boolean create(Long courseId, Question question) {
        try {
            Course course = courseService.getById(courseId);
            if (course == null) {
                System.out.println("Course not found");
                return false;
            }


            question.setCourse(course);
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


    public boolean update(Long courseId, Long questionId, Question updatedQuestion) {
        try {
            Course course = courseService.getById(courseId);
            if (course == null) {
                System.out.println("Course not found.");
                return false;
            }

            Question existingQuestion = questionRepository.findById(questionId).orElse(null);
            if (existingQuestion == null) {
                System.out.println("Question not found.");
                return false;
            }

            if (!existingQuestion.getCourse().getId().equals(courseId)) {
                System.out.println("Course id does not match");
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

    public Question getById(Long courseId, Long id) {
        try {
            Question question = null;
            if (questionRepository.findById(id).isPresent()) {
                question = questionRepository.findById(id).get();
            }
            if (question !=null && question.getCourse().getId().equals(courseId)) {
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

    public List<Question> getAll(Long courseId) {
        try {
            Course course = courseService.getById(courseId);
            if (course == null) {
                return null;
            }
            return questionRepository.findAllByCourseId(courseId);
        } catch (Exception e) {
            System.out.println("Error fetching question for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }

    public boolean delete(Long courseId, Long id) {
        try {
            Course course = courseService.getById(courseId);
            Question question = questionRepository.findById(id).orElse(null);
            if (course == null || question == null || !question.getCourse().getId().equals(courseId)  ) {
                return false;
            }
            questionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

