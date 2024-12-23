package org.lms.service.Submission;

import org.lms.entity.*;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.entity.User.Student;
import org.lms.repository.QuizSubmissionRepository;
import org.lms.service.AppUserService;
import org.lms.service.Assessment.QuizService;
import org.lms.service.QuestionService;
import org.lms.service.Feedback.QuizFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class QuizSubmissionService {
    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    private QuizService quizService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizFeedbackService quizFeedbackService;

    public QuizSubmission getById(Long id){
        return quizSubmissionRepository.findById(id).get();
    }

    public QuizSubmission getSubmition(Long courseId, Long quizId, Long studentId) {
        Quiz quiz = quizService.getById(courseId, quizId) ;
        if(quiz == null) return null;
        if(!Objects.equals(quiz.getCourse().getId(), courseId)) return null;
        QuizSubmission quizSubmission = quizSubmissionRepository.findByQuizAndStudent(quiz,(Student) appUserService.getById(studentId));
        return quizSubmission;
    }
    public List<QuizSubmission> getAllSubmitions(Long courseId, Long quizId){
        Quiz quiz = quizService.getById(courseId, quizId) ;
        if(quiz == null) return null;
        if(!Objects.equals(quiz.getCourse().getId(), courseId)) return null;
        List<QuizSubmission> submitedForms = quizSubmissionRepository.findByQuiz(quiz);
        return submitedForms;
    }
    public boolean create(Long courseId, Long quizId, Long studentId, QuizSubmission quizSubmission){
        Quiz quiz = quizService.getById(courseId, quizId);
        if(quiz == null) return false;
        Student student = (Student) appUserService.getById(studentId);
        if(student == null) return false;
        if(!Objects.equals(quiz.getCourse().getId(), courseId)) return false;

        quizSubmission.setQuiz(quiz);
        quizSubmission.setStudent(student);
        quizSubmissionRepository.save(quizSubmission);
        return true;
    }
    public Double grading(QuizSubmission quizSubmission, Long quizId, Long studentId, Long courseId){
        if(!quizSubmission.getStudent().getId().equals(studentId)) {
            return null;
        }
        if(!quizSubmission.getQuiz().getId().equals(quizId)) {
            return null;
        }
        List<QuestionAnswer> StudentAnswer = quizSubmission.getQuestionsAnswer();
        Double studentMark = 0.0;
        double max = 0.0;
        for(QuestionAnswer pair : StudentAnswer) {
            max+=1;
            Long questionId = pair.getQuestionId();
            String answer = pair.getAnswer();
            Question question = questionService.getById(courseId,questionId);
            if(question==null) {
                return null;
            }
            if(question.getAnswerFormat().getCorrectAnswer().equals(answer)) {
                studentMark += 1.0;
            }
        }
        QuizFeedback quizFeedback = new QuizFeedback();
        quizFeedback.setQuizSubmission(quizSubmission);
        quizFeedback.setGrade(studentMark);
        quizFeedback.setMaxGrade(max);
        quizFeedback.setComment("Automated quiz grading");
        quizFeedbackService.create(quizFeedback);
        return studentMark;
    }
    public boolean existsByStudentIdAndQuizId(Long studentId, Long quizId){
        return quizSubmissionRepository.existsByStudentIdAndQuizId(studentId,quizId);
    }
}