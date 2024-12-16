package org.lms.controller;

import org.lms.entity.Submission;
import org.lms.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/assignment/{assignmentId}/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @PostMapping
    public ResponseEntity<String> submitAssignment(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestParam("studentId") Long studentId,
            @RequestParam("file") MultipartFile file) {
        try
        {
            boolean success = submissionService.submit(courseId, assignmentId, studentId, file);
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
            @PathVariable Long submissionId,
            @RequestParam("studentId") Long studentId) {
        try {
            boolean success = submissionService.deleteSubmission(submissionId, studentId);
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
    public ResponseEntity<List<Submission>> getAllSubmissions(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId) {
        try {
            List<Submission> submissions = submissionService.getSubmissions(courseId, assignmentId);
            if (submissions == null || submissions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
