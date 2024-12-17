package org.lms.service;

import ch.qos.logback.core.joran.sanity.Pair;
import org.lms.entity.*;
import org.lms.repository.SubmitQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubmitQuizService {
    @Autowired
    private SubmitQuizRepository submitQuizRepository;
    @Autowired
    private QuizService quizService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private QuestionService questionService;
    public SubmitForm getSubmition(Long courseId, Long quizId, Long studentId) {
           Quiz quiz = quizService.getById(courseId, quizId) ;
           if(quiz == null) return null;
           if(!Objects.equals(quiz.getCourse().getId(), courseId)) return null;
           SubmitForm submitForm =submitQuizRepository.findByQuizAndStudent(quiz,(Student) appUserService.getById(studentId));
           return submitForm;
    }
    public List<SubmitForm> getAllSubmitions(Long courseId, Long quizId){
        Quiz quiz = quizService.getById(courseId, quizId) ;
        if(quiz == null) return null;
        if(!Objects.equals(quiz.getCourse().getId(), courseId)) return null;
        List<SubmitForm> submitedForms =submitQuizRepository.findByQuiz(quiz);
        return submitedForms;
    }
    public boolean create(Long courseId,Long quizId,Long studentId,SubmitForm submitForm){
        Quiz quiz = quizService.getById(courseId, quizId);
        if(quiz == null) return false;
        Student student = (Student) appUserService.getById(studentId);
        if(student == null) return false;
        if(!Objects.equals(quiz.getCourse().getId(), courseId)) return false;

//        System.out.println("lol2");
        submitForm.setQuiz(quiz);
//        System.out.println("lol3");
        submitForm.setStudent(student);
//        System.out.println("lol4");
        submitQuizRepository.save(submitForm);
//        System.out.println("lol5");
        return true;
    }
    public Double grading(SubmitForm submitForm, Long quizId,Long studentId,Long courseId){
        if(!submitForm.getStudent().getId().equals(studentId)) {
            return null;
        }
        if(!submitForm.getQuiz().getId().equals(quizId)) {
            return null;
        }
        List<QuestionAnswer> StudentAnswer = submitForm.getQuestionsAnswer();
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
        return studentMark;


    }
    public boolean existsByStudentIdAndQuizId(Long studentId, Long quizId){
        return submitQuizRepository.existsByStudentIdAndQuizId(studentId,quizId);
    }

}
