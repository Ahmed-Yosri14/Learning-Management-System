package org.lms.controller;


import org.lms.AuthorizationManager;
import org.lms.entity.Student;
import org.lms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseId}/lesson/{lessonId}/attendance/")
public class AttendanceRestController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    AuthorizationManager authorizationManager;

    // student
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("")
    public ResponseEntity<String> attend(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId, @RequestBody String otp) {
        if (!authorizationManager.checkCourseStudent(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (attendanceService.tryRecord(1L, lessonId, courseId, otp)){
            return ResponseEntity.ok("Student has attended the lesson!");
        }
        return ResponseEntity.ok("Student hasn't attended the lesson");
    }

    // instructor & admin
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("{studentId}/")
    public ResponseEntity<String> getByStudentId(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId, @PathVariable("studentId") Long studentId) {
        if (!authorizationManager.checkCourseViewConfidential(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (attendanceService.checkStudentId(studentId, lessonId, courseId)){
            return ResponseEntity.ok("Student has attended the lesson!");
        }
        return ResponseEntity.ok("Student hasn't attended the lesson");
    }
    // instructor & admin
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<Student>> getAll(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId) {
        if (!authorizationManager.checkCourseViewConfidential(courseId)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(attendanceService.getByLessonId(lessonId, courseId));
    }
}
