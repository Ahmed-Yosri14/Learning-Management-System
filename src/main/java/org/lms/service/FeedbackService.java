package org.lms.service;

import org.apache.coyote.Request;
import org.lms.entity.AppUser;
import org.lms.entity.Feedback;
import org.lms.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
    public boolean updateFeedback(Feedback feedback) {
        try {
            if (feedbackRepository.existsById(feedback.getId())) {
                feedbackRepository.save(feedback);
            }
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean deleteFeedback(Long id) {
        try {
            if (feedbackRepository.existsById(id)) {
                feedbackRepository.deleteById(id);
            }
            return true;
        }
        catch(Exception e){}
        return false;
    }
}