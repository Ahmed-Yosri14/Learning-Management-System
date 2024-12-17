package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.AssignmentFeedback;
import org.lms.service.AssignmentFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/assignment/{assignmentId}/submission/{assignmentSubmissionId}/feedback")
public class AssignmentFeedbackRestController {

    @Autowired
    AssignmentFeedbackService assignmentFeedbackService;

    @Autowired
    private AuthorizationManager authorizationManager;

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> create(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @RequestBody AssignmentFeedback assignmentFeedback
    ) {
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (assignmentFeedbackService.create(courseId, assignmentId, assignmentSubmissionId, assignmentFeedback)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @PathVariable Long id,
            @RequestBody AssignmentFeedback assignmentFeedback
    ){

        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (assignmentFeedbackService.update(courseId, assignmentId, id, assignmentSubmissionId, assignmentFeedback)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @PathVariable Long id
    ){

        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (assignmentFeedbackService.delete(courseId, assignmentSubmissionId, assignmentId, id)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // instructor or admin
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<AssignmentFeedback> getById(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId,
            @PathVariable Long id
    ){

        if (!authorizationManager.canViewCourse(courseId)){
            return ResponseEntity.status(403).build();
        }
        AssignmentFeedback assignmentFeedback = assignmentFeedbackService.getById(courseId, assignmentId, assignmentSubmissionId, id);
        if (assignmentFeedback == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignmentFeedback);
    }

    @GetMapping("")
    public ResponseEntity<List<AssignmentFeedback>> getAll(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId
    ){
        if (!(authorizationManager.isAdminOrInstructor(courseId))){
            return ResponseEntity.status(403).build();
        }
        List<AssignmentFeedback> feedbackList = assignmentFeedbackService.getAllByAssignmentSubmissionId(courseId, assignmentId, assignmentSubmissionId);
        if (feedbackList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(feedbackList);
    }
}
