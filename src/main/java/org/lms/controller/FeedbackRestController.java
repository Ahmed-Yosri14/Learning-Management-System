package org.lms.controller;

import jakarta.validation.Valid;
import org.lms.entity.Feedback;
import org.lms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackRestController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable("id") Long id) {
        return feedbackService.getFeedbackById(id);
    }

    @PostMapping("/")
    public Feedback saveFeedback(@RequestBody @Valid Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFeedback(@PathVariable("id") Long id, @RequestBody @Valid Feedback feedback) {
        feedback.setId(id); // Ensure the feedback ID matches the path variable
        boolean isUpdated = feedbackService.updateFeedback(feedback);

        if (isUpdated) {
            return new ResponseEntity<>("Feedback updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Feedback not found!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable("id") Long id) {
        boolean isDeleted = feedbackService.deleteFeedback(id);

        if (isDeleted) {
            return new ResponseEntity<>("Feedback deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Feedback not found!", HttpStatus.NOT_FOUND);
        }
    }
}
