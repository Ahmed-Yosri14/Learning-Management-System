package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.service.Feedback.AssignmentFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/assignment/{assignmentId}/submission/{assignmentSubmissionId}/feedback")
public class AssignmentFeedbackRestController {

    @Autowired
    private AssignmentFeedbackService assignmentFeedbackService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<Object> create(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @RequestBody AssignmentFeedback assignmentFeedback
    ) {
        if (!assignmentFeedbackService.getAssignmentSubmissionService().existsById(courseId, assignmentId, assignmentSubmissionId)){
            return ResponseEntity.status(404).build();
        }
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (assignmentFeedbackService.create(assignmentSubmissionId, assignmentFeedback)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @PathVariable Long id,
            @RequestBody AssignmentFeedback assignmentFeedback
    ){
        if (!assignmentFeedbackService.existsById(courseId, assignmentId, assignmentSubmissionId, id)){
            return ResponseEntity.status(404).build();
        }
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (assignmentFeedbackService.update(assignmentSubmissionId, assignmentFeedback)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @PathVariable Long id
    ){
        if (!assignmentFeedbackService.existsById(courseId, assignmentId, assignmentSubmissionId, id)){
            return ResponseEntity.status(404).build();
        }
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (assignmentFeedbackService.delete(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // all
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @PathVariable Long id
    ){
        if (!assignmentFeedbackService.existsById(courseId, assignmentId, assignmentSubmissionId, id)){
            return ResponseEntity.status(404).build();
        }
        if (!authorizationManager.canAccessSubmissionDetails(courseId, assignmentSubmissionId)){
            return ResponseEntity.status(403).build();
        }
        AssignmentFeedback assignmentFeedback = assignmentFeedbackService.getById(id);
        if (assignmentFeedback == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(assignmentFeedback));
    }

    // all
    @GetMapping("")
    public ResponseEntity<Object> getAll(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId
    ){
        if (!assignmentFeedbackService.getAssignmentSubmissionService().existsById(courseId, assignmentId, assignmentSubmissionId)){
            return ResponseEntity.status(404).build();
        }
        if (!authorizationManager.canAccessSubmissionDetails(courseId, assignmentSubmissionId)){
            return ResponseEntity.status(403).build();
        }
        List<AssignmentFeedback> feedbackList = assignmentFeedbackService.getAllByAssignmentSubmissionId(assignmentSubmissionId);
        if (feedbackList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(feedbackList)));
    }
}