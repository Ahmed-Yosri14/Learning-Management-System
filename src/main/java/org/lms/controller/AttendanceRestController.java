package org.lms.controller;


import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.dao.request.AttendanceRequest;
import org.lms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/course/{courseId}/lesson/{lessonId}/attendance")
public class AttendanceRestController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    // student
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("")
    public ResponseEntity<String> attend(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId, @RequestBody AttendanceRequest request) {
        System.out.println(request.getOtp());
        if (!authorizationManager.isEnrolled(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (attendanceService.tryRecord(authorizationManager.getCurrentUserId(), lessonId, courseId, request.getOtp())){
            return ResponseEntity.ok("Lesson Attended");
        }
        return ResponseEntity.ok("Error");
    }

    // instructor & admin
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("/{studentId}")
    public ResponseEntity<String> getByStudentId(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId, @PathVariable("studentId") Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)){
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
    public ResponseEntity<Object> getAll(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(attendanceService.getByLessonId(lessonId, courseId))));
    }
}