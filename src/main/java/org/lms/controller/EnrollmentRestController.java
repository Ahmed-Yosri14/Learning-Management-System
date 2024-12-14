package org.lms.controller;


import org.lms.entity.Student;
import org.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course/{courseId}/enrollment")
public class EnrollmentRestController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PutMapping("/{studentId}/")
    public ResponseEntity<String> create(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (enrollmentService.create(studentId, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @DeleteMapping("/{studentId}/")
    public ResponseEntity<String> delete(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (enrollmentService.delete(studentId, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{studentId}/")
    public ResponseEntity<String> getByStudentId(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        if (enrollmentService.checkStudentId(studentId, courseId)){
            return ResponseEntity.ok("Student is enrolled in the course!");
        }
        return ResponseEntity.ok("Student isn't enrolled in the course");
    }
    @GetMapping("/")
    public List<Student> getAll(@PathVariable("courseId") Long courseId) {
        return enrollmentService.getByCourseId(courseId);
    }
}
