package org.lms.service.Feedback;

import org.lms.entity.Feedback.Feedback;
import org.lms.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public boolean existsById(Long id) {
        try {
            return feedbackRepository.existsById(id);
        } catch(Exception e) {
            return false;
        }
    }
    public Feedback getById(Long id) {
        try {
            return feedbackRepository.findById(id).orElse(null);
        } catch(Exception e) {
            return null;
        }
    }
    public boolean update(Long id, Feedback feedback){
        try {

            Feedback oldFeedBack = getById(id);

            if (feedback.getGrade() != null) {
                oldFeedBack.setGrade(feedback.getGrade());
            }
            if (feedback.getMaxGrade() != null) {
                oldFeedBack.setMaxGrade(feedback.getMaxGrade());
            }
            if (feedback.getComment() != null) {
                oldFeedBack.setComment(feedback.getComment());
            }
            feedbackRepository.save(oldFeedBack);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long id){
        try {
            feedbackRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}