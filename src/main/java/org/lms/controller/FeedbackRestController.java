package org.lms.controller;

import org.lms.entity.Feedback;
import org.lms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Feedback saveFeedback(@RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable("id") Long id) {
        feedbackService.deleteFeedback(id);
    }
}
