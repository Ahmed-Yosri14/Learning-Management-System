package org.lms.controller;

import org.lms.entity.Attendance;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressServiceController {

    @Autowired
    private ProgressService progressService;

    @GetMapping("/quiz-scores/{courseId}")
    public ResponseEntity<List<QuizFeedback>> getQuizScores(@PathVariable Long courseId) {
        List<QuizFeedback> quizScores = progressService.getQuizScores(courseId);
        return ResponseEntity.ok(quizScores);
    }

    @GetMapping("/quiz-scores/{courseId}/student/{studentId}")
    public ResponseEntity<List<QuizFeedback>> getQuizScores(@PathVariable Long courseId,@PathVariable Long studentId) {
        List<QuizFeedback> quizScores = progressService.getQuizScoresByCourseIdAndStudentId(courseId,studentId);
        return ResponseEntity.ok(quizScores);
    }

    @GetMapping("/assignment-scores/{courseId}")
    public ResponseEntity<List<AssignmentFeedback>> getAssignmentSubmissions(@PathVariable Long courseId) {
        List<AssignmentFeedback> assignmentFeedbacks = progressService.getAssignmentFeedbacks(courseId);
        return ResponseEntity.ok(assignmentFeedbacks);
    }

    @GetMapping("/attendance/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceRecords(@PathVariable Long courseId) {
        List<Attendance> attendanceRecords = progressService.getAttendanceRecords(courseId);
        return ResponseEntity.ok(attendanceRecords);
    }

    @GetMapping("/attendance/{courseId}/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceRecordsForStudent(@PathVariable Long courseId,@PathVariable Long studentId)
    {
        List<Attendance> attendanceRecords = progressService.getAttendanceRecords(courseId);
        return ResponseEntity.ok(attendanceRecords);
    }
}
