package org.lms.service;

import org.lms.entity.Attendance;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.repository.AttendanceRepository;
import org.lms.repository.QuizFeedbackRepository;
import org.lms.repository.AssignmentFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {


    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private QuizFeedbackRepository quizFeedbackRepository;

    @Autowired
    private AssignmentFeedbackRepository assignmentFeedbackRepository;

    public List<QuizFeedback> getQuizScores(Long courseId) {
        return quizFeedbackRepository.findAllByCourseId(courseId);
    }

    public List<AssignmentFeedback> getAssignmentFeedbacks(Long courseId) {
        return assignmentFeedbackRepository.findByCourseId(courseId);
    }
    public List<Attendance> getAttendanceRecords( Long courseId) {
        return attendanceRepository.findByCourseId( courseId);
    }
    public List<Attendance> getAttendanceRecordsPerStudent( Long courseId,Long studentId) {
        return attendanceRepository.findByCourseIdAndStudentId(courseId, studentId);
    }

}
