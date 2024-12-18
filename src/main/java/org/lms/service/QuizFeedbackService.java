package org.lms.service;

import org.lms.entity.AssignmentFeedback;
import org.lms.entity.QuizFeedback;
import org.lms.repository.QuizFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizFeedbackService extends FeedbackService {

    @Autowired
    QuizFeedbackRepository quizFeedbackRepository;

    public boolean existsById(
            Long courseId,
            Long quizId,
            Long quizSubmissionId,
            Long id
    ){

        AssignmentFeedback assignmentFeedback = (AssignmentFeedback)getById(id);
        return existsById(id)
                && assignmentFeedback.getAssignmentSubmission().getId().equals(quizSubmissionId);
    }
    public AssignmentFeedback getById(
            Long courseId,
            Long quizId,
            Long quizSubmissionId,
            Long id
    ){
        try {
            assert existsById(courseId, quizId, quizSubmissionId, id);
            return (AssignmentFeedback)getById(id);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public boolean create(QuizFeedback quizFeedback){
        try {
            quizFeedbackRepository.save(quizFeedback);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public List<QuizFeedback> getAllByQuizSubmissionId(
            Long courseId,
            Long quizId,
            Long quizSubmissionId
    ){
        try {
            return quizFeedbackRepository.findAllByQuizSubmissionId(quizSubmissionId);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}