package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Assessment.Assignment;
import org.lms.service.Assessment.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course/{courseid}/assignment")
public class AssignmentRestController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> createAssignment(@PathVariable("courseid") Long courseId, @RequestBody Assignment assignment) {
        if (!(authorizationManager.isInstructor(courseId))) {
            return ResponseEntity.badRequest().body("You do not have permission to edit this course.");
        }
        if (assignmentService.create(courseId, assignment)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAssignment(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id, @RequestBody Assignment assignment) {
        if (!(authorizationManager.isInstructor(courseId))) {
            return ResponseEntity.badRequest().body("You do not have permission to edit this course.");
        }
        if (assignmentService.update(courseId, id, assignment)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!(authorizationManager.hasAccess(courseId))) {
            return ResponseEntity.status(403).body(null);
        }
        Assignment assignment = assignmentService.getById(courseId, id);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(assignment));
    }

    @GetMapping("")
    public ResponseEntity<Object>  getAll(@PathVariable("courseid") Long courseId) {
        if (!(authorizationManager.hasAccess(courseId))) {
            return ResponseEntity.status(403).body(null);
        }
        List<Assignment> assignments = assignmentService.getAll(courseId);
        if (assignments != null && !assignments.isEmpty()) {
            return ResponseEntity.ok(entityMapper.map(new ArrayList<>(assignments)));
        }
        return ResponseEntity.ok().body(assignments);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!(authorizationManager.isInstructor(courseId))) {
            return ResponseEntity.badRequest().body("You do not have permission to edit this course.");
        }
        if (assignmentService.deleteAssessment(courseId, id)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}