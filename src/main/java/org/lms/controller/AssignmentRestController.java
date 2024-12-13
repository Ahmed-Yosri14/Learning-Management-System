package org.lms.controller;

import org.lms.entity.Assignment;
import org.lms.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentRestController {
    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/")
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }

    @GetMapping("/{id}")
    public Assignment getAssignment(@PathVariable("id") Long id) {
        return assignmentService.getAssignment(id);
    }

    @GetMapping("/course/{courseId}")
    public List<Assignment> getAssignmentsByCourse(@PathVariable("courseId") Long courseId) {
        return assignmentService.getAssignmentsByCourseId(courseId);
    }
}
