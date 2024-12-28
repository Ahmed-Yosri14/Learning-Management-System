package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.service.ExcelGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/course/{courseId}/excel")
public class ExcelGeneratingRestController {

    @Autowired
    private ExcelGeneratorService excelGeneratingService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @GetMapping("")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('INSTRUCTOR')")
    public ResponseEntity<String> generateExcel(@PathVariable("courseId") Long courseId) {
        try {
            if (authorizationManager.isAdminOrInstructor(courseId)) {
                String filePath = excelGeneratingService.generateStudentProgressExcel(courseId);
                return ResponseEntity.ok("Excel sheet generated successfully at " + filePath);
            } else {
                return ResponseEntity.status(403).body("You are not allowed to generate sheets");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong while creating the Sheet.");
        }
    }
}
