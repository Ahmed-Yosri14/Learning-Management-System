package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.Assignment;
import org.lms.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseid}/assignment")
public class AssignmentRestController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @PutMapping("")
    public ResponseEntity<String> createAssignment(@PathVariable("courseid") Long courseId, @RequestBody Assignment assignment) {
        if (!(authorizationManager.checkCourseEdit(courseId))) {
            return ResponseEntity.badRequest().body("You do not have permission to edit this course.");
        }
        if (assignmentService.create(courseId, assignment)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAssignment(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id, @RequestBody Assignment assignment) {
        if (!(authorizationManager.checkCourseEdit(courseId))) {
            return ResponseEntity.badRequest().body("You do not have permission to edit this course.");
        }
        if (assignmentService.update(courseId, id, assignment)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!(authorizationManager.checkCourseView(courseId))) {
            return ResponseEntity.status(403).body(null);
        }
        Assignment assignment = assignmentService.getById(courseId, id);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignment);
    }

    @GetMapping("")
    public ResponseEntity<List<Assignment>>  getAll(@PathVariable("courseid") Long courseId) {
        if (!(authorizationManager.checkCourseView(courseId))) {
            return ResponseEntity.status(403).body(null);
        }
        List<Assignment> assignments = assignmentService.getAll(courseId);
        if (assignments != null && !assignments.isEmpty()) {
            return ResponseEntity.ok(assignments);
        }
        return ResponseEntity.ok().body(assignments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!(authorizationManager.checkCourseEdit(courseId))) {
            return ResponseEntity.badRequest().body("You do not have permission to edit this course.");
        }
        if (assignmentService.delete(courseId, id)) {
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}