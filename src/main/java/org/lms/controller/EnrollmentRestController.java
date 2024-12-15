package org.lms.controller;


import org.lms.AuthorizationManager;
import org.lms.entity.Student;
import org.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course/{courseId}/enrollment/")
public class EnrollmentRestController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AuthorizationManager authorizationManager;

    // student
    @PutMapping("")
    public ResponseEntity<String> enroll(@PathVariable("courseId") Long courseId) {
        if (enrollmentService.create(1L, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // student
    @DeleteMapping("")
    public ResponseEntity<String> unenroll(@PathVariable("courseId") Long courseId) {
        if (enrollmentService.delete(1L, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // instructor
    @DeleteMapping("{studentId}/")
    public ResponseEntity<String> delete(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (!authorizationManager.checkCourseEdit(courseId, 1L)){
            return ResponseEntity.status(403).build();
        }
        if (enrollmentService.delete(studentId, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // instructor & admin
    @GetMapping("{studentId}/")
    public ResponseEntity<String> getByStudentId(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (!authorizationManager.checkCourseViewConfidential(courseId, 1L)){
            return ResponseEntity.status(403).build();
        }
        if (enrollmentService.checkStudentId(studentId, courseId)){
            return ResponseEntity.ok("Student is enrolled in the course!");
        }
        return ResponseEntity.ok("Student isn't enrolled in the course");
    }
    // instructor & admin
    @GetMapping("")
    public ResponseEntity<List<Student>> getAll(@PathVariable("courseId") Long courseId) {
        if (!authorizationManager.checkCourseViewConfidential(courseId, 1L)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(enrollmentService.getByCourseId(courseId));
    }
}
