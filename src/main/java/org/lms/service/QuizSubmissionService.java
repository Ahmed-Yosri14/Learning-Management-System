package org.lms.service;

import org.lms.entity.*;
import org.lms.repository.QuizSubmissionRepository;
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

//    public boolean existsById(
//            Long courseId,
//            Long quizId,
//            Long id
//    ){
//        QuizSubmission quizSubmission = getById(id);
//        return quizSubmissionRepository.existsById(id)
//                && quizService.existsById(courseId, quizId)
//                && assignmentSubmission.getAssignment().getId().equals(quizId);
//    }

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

//        System.out.println("lol2");
        quizSubmission.setQuiz(quiz);
//        System.out.println("lol3");
        quizSubmission.setStudent(student);
//        System.out.println("lol4");
        quizSubmissionRepository.save(quizSubmission);
//        System.out.println("lol5");
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
        for(QuestionAnswer pair : StudentAnswer) {
            Long questionId = pair.getQuestionId();
            String answer = pair.getAnswer();
            Question question = questionService.getById(courseId,quizId,questionId);
            if(question==null) {
                return null;
            }
            if(question.getAnswerFormat().getCorrectAnswer().equals(answer)) {
                studentMark += question.getMark();
            }
        }
        QuizFeedback quizFeedback = new QuizFeedback();
        quizFeedback.setQuizSubmission(quizSubmission);
        quizFeedback.setGrade(studentMark);
        quizFeedback.setMaxGrade(quizService.getFullMark(quizId));
        quizFeedback.setComment("Automated quiz grading");
        quizFeedbackService.create(quizFeedback);
        return studentMark;
    }
    public boolean existsByStudentIdAndQuizId(Long studentId, Long quizId){
        return quizSubmissionRepository.existsByStudentIdAndQuizId(studentId,quizId);
    }

}
