package org.lms.controller;

import org.lms.EntityMapper;
import org.lms.entity.Attendance;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressServiceController {

    @Autowired
    private ProgressService progressService;
    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("/quiz-scores/{courseId}")
    public ResponseEntity<Object> getQuizScores(@PathVariable Long courseId) {
        List<QuizFeedback> quizScores = progressService.getQuizScores(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(quizScores)));
    }

    @GetMapping("/quiz-scores/{courseId}/student/{studentId}")
    public ResponseEntity<Object> getQuizScores(@PathVariable Long courseId,@PathVariable Long studentId) {
        List<QuizFeedback> quizScores = progressService.getQuizScoresByCourseIdAndStudentId(courseId,studentId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(quizScores)));
    }

    @GetMapping("/assignment-scores/{courseId}")
    public ResponseEntity<Object> getAssignmentSubmissions(@PathVariable Long courseId) {
        List<AssignmentFeedback> assignmentFeedbacks = progressService.getAssignmentFeedbacks(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(assignmentFeedbacks)));
    }

    @GetMapping("/attendance/{courseId}")
    public ResponseEntity<Object> getAttendanceRecords(@PathVariable Long courseId) {
        List<Attendance> attendanceRecords = progressService.getAttendanceRecords(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(attendanceRecords)));
    }

    @GetMapping("/attendance/{courseId}/student/{studentId}")
    public ResponseEntity<Object> getAttendanceRecordsForStudent(@PathVariable Long courseId,@PathVariable Long studentId) {
        List<Attendance> attendanceRecords = progressService.getAttendanceRecords(courseId);
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(attendanceRecords)));
    }
}
