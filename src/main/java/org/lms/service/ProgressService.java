package org.lms.service;

import org.lms.entity.Attendance;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.repository.AssignmentSubmissionRepository;
import org.lms.repository.AttendanceRepository;
import org.lms.repository.QuizSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<QuizSubmission> getQuizScores(Long studentId) {
        return quizSubmissionRepository.findByCourseId(studentId);
    }

    public List<AssignmentSubmission> getAssignmentSubmissions(Long studentId) {
        return assignmentSubmissionRepository.findByCourseId(studentId);
    }
    public List<Attendance> getAttendanceRecords( Long courseId) {
        return attendanceRepository.findByCourseId( courseId);
    }

}
