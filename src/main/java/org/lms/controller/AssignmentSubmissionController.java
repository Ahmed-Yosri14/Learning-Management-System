package org.lms.controller;

import org.lms.entity.AssignmentSubmission;
import org.lms.service.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/assignment/{assignmentId}/submission")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;
    @PostMapping
    public ResponseEntity<String> submitAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestParam("studentId") Long studentId,
            @RequestParam("file") MultipartFile file) {
        try
        {
            boolean success = assignmentSubmissionService.create(courseId, assignmentId, studentId, file);
            if (success)
            {
                return ResponseEntity.ok("Assignment submitted successfully.");
            }
            else
            {
                return ResponseEntity.badRequest().body("Failed to submit the assignment.");
            }
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{submissionId}")
    public ResponseEntity<String> deleteSubmission(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @PathVariable Long assignmentSubmissionId
    ) {
        try {
            boolean success = assignmentSubmissionService.delete(courseId, assignmentId, assignmentSubmissionId);
            if (success) {
                return ResponseEntity.ok("Submission deleted successfully.");
            } else {
                return ResponseEntity.badRequest().body("Failed to delete the submission.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    // we have to restrict the access to this method
    @GetMapping
    public ResponseEntity<List<AssignmentSubmission>> getAllSubmissions(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId) {
        try {
            List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionService.getAllByAssignmentId(courseId, assignmentId);
            if (assignmentSubmissions == null || assignmentSubmissions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(assignmentSubmissions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
