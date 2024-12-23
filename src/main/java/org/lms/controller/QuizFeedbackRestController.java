package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.service.Feedback.FeedbackService;
import org.lms.service.Feedback.QuizFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/quiz/{quizId}/submission/{quizSubmissionId}/feedback")
public class QuizFeedbackRestController {

    @Autowired
    private QuizFeedbackService quizFeedbackService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;
    // all
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(
            @PathVariable Long courseId,
            @PathVariable Long quizId,
            @PathVariable Long quizSubmissionId,
            @PathVariable Long id
    ){

        if (!canAccessDetails(courseId, quizSubmissionId)){
            return ResponseEntity.status(403).build();
        }
        AssignmentFeedback assignmentFeedback = quizFeedbackService.getById(courseId, quizId, quizSubmissionId, id);
        if (assignmentFeedback == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(assignmentFeedback));
    }

    // all
    @GetMapping("")
    public ResponseEntity<Object> getAll(
            @PathVariable Long courseId,
            @PathVariable Long quizId,
            @PathVariable Long quizSubmissionId
    ){
        if (!canAccessDetails(courseId, quizSubmissionId)){
            return ResponseEntity.status(403).build();
        }
        List<QuizFeedback> feedbackList = quizFeedbackService.getAllByQuizSubmissionId(courseId, quizId, quizSubmissionId);
        if (feedbackList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(feedbackList)));
    }

    private boolean canAccessDetails(Long courseId, Long submissionId){
        return authorizationManager.isAdminOrInstructor(courseId) || authorizationManager.ownsSubmission(submissionId);
    }
}
