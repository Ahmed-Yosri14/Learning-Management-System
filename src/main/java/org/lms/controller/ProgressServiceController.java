package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Attendance;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressServiceController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private AuthorizationManager authorizationManager;

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/quiz-scores/{courseId}")
    public ResponseEntity<Object> getQuizScores(@PathVariable Long courseId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<QuizFeedback> quizScores = progressService.getQuizScores(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(quizScores)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/quiz-scores/{courseId}/student/{studentId}")
    public ResponseEntity<Object> getQuizScores(@PathVariable Long courseId,@PathVariable Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<QuizFeedback> quizScores = progressService.getQuizScoresByCourseIdAndStudentId(courseId,studentId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(quizScores)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/assignment-scores/{courseId}")
    public ResponseEntity<Object> getAssignmentSubmissions(@PathVariable Long courseId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<AssignmentFeedback> assignmentFeedbacks = progressService.getAssignmentFeedbacks(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(assignmentFeedbacks)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/assignment-scores/{courseId}/student/{studentId}")
    public ResponseEntity<Object> getAssignmentSubmissionsByStudent(@PathVariable Long courseId,@PathVariable Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<AssignmentFeedback> assignmentFeedbacks = progressService.getAssignmentFeedbacksByCourseAndStudent(courseId,studentId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(assignmentFeedbacks)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/attendance/{courseId}")
    public ResponseEntity<Object> getAttendanceRecords(@PathVariable Long courseId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Attendance> attendanceRecords = progressService.getAttendanceRecords(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(attendanceRecords)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/attendance/{courseId}/student/{studentId}")
    public ResponseEntity<Object> getAttendanceRecordsForStudent(@PathVariable Long courseId,@PathVariable Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Attendance> attendanceRecords = progressService.getAttendanceRecords(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(attendanceRecords)));
    }
}