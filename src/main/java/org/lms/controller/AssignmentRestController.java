package org.lms.controller;

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

    @PutMapping("/")
    public ResponseEntity<String> createAssignment(@PathVariable("courseid") Long courseId,@RequestBody Assignment assignment) {
        if (assignmentService.create(courseId,assignment)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<String> updateAssignment(@PathVariable("courseid") Long courseId,@PathVariable("id") Long id, @RequestBody Assignment assignment) {
        if (assignmentService.update(courseId, id,assignment)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{id}/")
    public ResponseEntity<Assignment> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        Assignment assignment = assignmentService.getById(courseId,id);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignment);
    }
    @GetMapping("/")
    public List<Assignment> getAll(@PathVariable("courseid") Long courseId) {
        return assignmentService.getAll(courseId);
    }
    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteAssignment(@PathVariable("courseid") Long courseId,@PathVariable("id") Long id) {
        if(assignmentService.delete(courseId,id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}