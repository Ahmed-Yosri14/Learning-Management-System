package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.service.Submission.AssignmentSubmissionService;
import org.lms.service.Submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/assignment/{assignmentId}/submission")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;
    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("")
    public ResponseEntity<String> submitAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file
    ) {
//        if (!assignmentSubmissionService.assignmentService.existsById(courseId, assignmentId)) {
//            return ResponseEntity.status(404).body("Assignment not found.");
//        }
        if (!authorizationManager.isEnrolled(courseId)) {
            return ResponseEntity.status(403).body("You don't have permission to submit.");
        }
        if (assignmentSubmissionService.create(courseId, assignmentId, authorizationManager.getCurrentUserId(), file)) {
            return ResponseEntity.ok("Submission uploaded successfully.");
        }
        return ResponseEntity.badRequest().body("Something went wrong during submission.");
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{submissionId}")
    public ResponseEntity<String> deleteSubmission(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long submissionId
    ) {
        if (!assignmentSubmissionService.existsById(courseId, assignmentId, submissionId)) {
            return ResponseEntity.status(404).body("Submission not found.");
        }
        if (!authorizationManager.ownsSubmission(submissionId)) {
            return ResponseEntity.status(403).body("You don't have permission to delete this submission.");
        }
        if (assignmentSubmissionService.delete(courseId, assignmentId, submissionId)) {
            return ResponseEntity.ok("Submission deleted successfully.");
        }
        return ResponseEntity.badRequest().body("Something went wrong during deletion.");
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<Object> getSubmissionById(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long submissionId
    ) {
        if (!assignmentSubmissionService.existsById(courseId, assignmentId, submissionId)) {
            return ResponseEntity.status(404).body(null);
        }
        if (!authorizationManager.isAdminOrInstructor(courseId) && !authorizationManager.ownsSubmission(submissionId)) {
            return ResponseEntity.status(403).body(null);
        }
        AssignmentSubmission assignmentSubmission = assignmentSubmissionService.getById(courseId, assignmentId, submissionId);
        if (assignmentSubmission == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(assignmentSubmission));
    }

    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllSubmissions(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId
    ) {

        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).build();
        }
        List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionService.getAllByAssignmentId(courseId, assignmentId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(assignmentSubmissions)));
    }
}
