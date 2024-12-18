package org.lms.service;

import org.lms.entity.Feedback.Feedback;
import org.lms.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public boolean existsById(Long id){
        return feedbackRepository.existsById(id);
    }
    public Feedback getById(Long id){
        return feedbackRepository.findById(id).get();
    }
    public boolean update(Long id, Feedback feedback){
        try {
            assert existsById(id);

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