package org.lms.controller;


import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/course/{courseId}/enrollment")
public class EnrollmentRestController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    // student
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("")
    public ResponseEntity<String> enroll(@PathVariable("courseId") Long courseId) {
        if (enrollmentService.create(authorizationManager.getCurrentUserId(), courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // student
    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("")
    public ResponseEntity<String> unroll(@PathVariable("courseId") Long courseId) {
        if (enrollmentService.delete(authorizationManager.getCurrentUserId(), courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> delete(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (enrollmentService.delete(studentId, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // instructor & admin
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("/{studentId}")
    public ResponseEntity<String> getByStudentId(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (enrollmentService.checkStudentId(studentId, courseId)){
            return ResponseEntity.ok("Student is enrolled in the course!");
        }
        return ResponseEntity.ok("Student isn't enrolled in the course");
    }
    // instructor & admin
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getAll(@PathVariable("courseId") Long courseId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(enrollmentService.getByCourseId(courseId))));
    }
}