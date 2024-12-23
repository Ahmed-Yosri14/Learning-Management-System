package org.lms.service.Assessment;

import io.jsonwebtoken.lang.Assert;
import jakarta.transaction.Transactional;
import org.lms.AuthorizationManager;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Course;
import org.lms.entity.Question;
import org.lms.entity.StudentQuiz;
import org.lms.entity.User.Student;
import org.lms.repository.QuizRepository;
import org.lms.repository.StudentQuizRepository;
import org.lms.service.CourseService;
import org.lms.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QuizService extends AssessmentService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private StudentQuizRepository studentQuizRepository;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private CourseService courseService;

    public boolean create(Long courseId, Quiz quiz)
    {
        try{
            Assert.isTrue(super.createAssessment(courseId, quiz));
            Course course = courseService.getById(courseId);
            notificationService.createToAllEnrolled(
                    courseId,
                    "New Quiz",
                    "New quiz was just added in \'" + course.getName() + "\'!"
            );
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean update(Long courseId, Long id, Quiz quiz)
    {
        return super.updateAssessment(courseId, id, quiz);
    }
    public boolean delete(Long courseId, Long id) {
        return super.deleteAssessment(courseId, id);
    }
    public Map<String,Object> getById(Long courseId, Long id)
    {
        try {
            Quiz quiz = (Quiz) super.getById(id);
            return quiz.toMap(authorizationManager.);
        } catch (Exception e) {
            System.out.println("Error retrieving quiz: " + e.getMessage());
        }
        return null;
    }
    public List<Quiz> getAll(Long courseId){
        try {
            Assert.isTrue(courseService.existsById(courseId));
            Course course = courseService.getById(courseId);

            return quizRepository.findAllByCourse(course);
        } catch (Exception e) {
            System.out.println("Error fetching quiz's for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    @Transactional
    public boolean removeQuestionsFromQuiz(Long courseId, Long quizId, List<Long> questionIds) {
        try {
            Quiz quiz = quizRepository.findById(quizId).orElse(null);
            if (quiz == null || !quiz.getCourse().getId().equals(courseId)) {
                return false;
            }
            for (Long questionId : questionIds) {
                Question question = questionService.getById(courseId,questionId);
                if(question == null){
                    continue;
                }
                quiz.getQuestions().remove(question);
            }
            quizRepository.save(quiz);
            return true;

        } catch (Exception e) {
            e.printStackTrace(); // Debugging purposes
            return false;
        }
    }
    @Transactional
    public boolean addQuestionstoQuiz(Long courseId, Long quizId, List<Long> questionIds) {
        try {
            Quiz quiz = quizRepository.findById(quizId).orElse(null);
            if (quiz == null || !quiz.getCourse().getId().equals(courseId)) {
                return false;
            }
            for (Long questionId : questionIds) {
                Question question = questionService.getById(courseId,questionId);
                if(question == null){
                    continue;
                }
                if(quiz.getQuestions().contains(question)){
                    continue;
                }

                quiz.getQuestions().add(question);
            }
            quizRepository.save(quiz);
            return true;

        } catch (Exception e) {
            e.printStackTrace(); // Debugging purposes
            return false;
        }
    }


    public boolean courseExistsById(Long courseId){
        return courseService.existsById(courseId);
    }


}

