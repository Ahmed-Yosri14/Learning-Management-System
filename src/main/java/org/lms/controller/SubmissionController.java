package org.lms.controller;

import org.lms.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/assignment")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<String> submitAssignment(@PathVariable Long assignmentId,
                                                   @RequestParam Long studentId,
                                                   @RequestParam("file") MultipartFile file) {
        try {
            if (submissionService.submitAssignment(assignmentId, studentId, file)) {
                return ResponseEntity.ok("Assignment submitted successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid assignment or student");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
