package org.lms.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lms.entity.Attendance;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.entity.User.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
public class VisualizationService {

    @Autowired
    EnrollmentService enrollmentService;
    @Autowired
    ProgressService progressService;
    @Autowired
    private LessonService lessonService;

    public String generatePieChart(Long courseId)throws IOException{
        DefaultPieDataset dataset = new DefaultPieDataset();
        int[]grades = getStudentsTotalGrades(courseId);
        for (int i = 0; i < grades.length; i++) {
            dataset.setValue((i * 10) + "%", (grades[i] / (double) grades.length) * 100);
        }
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Grades Percentage Distribution - Course " + courseId,
                dataset,
                true,
                true,
                false
        );
        String chartPath = "src/main/resources/charts/QuizScores_" + courseId + "_PieChart.png";
        File chartFile = new File(chartPath);
        chartFile.getParentFile().mkdirs();
        ChartUtils.saveChartAsPNG(chartFile, pieChart, 800, 600);
        return chartPath;
    }

    public double getStudentScores(Student student, Long courseId){
        Long studentId = student.getId();
        double score = 0;
        double maxScore = 0;
        System.out.println(studentId);
        System.out.println(courseId);
        List<QuizFeedback> quizScores = progressService.getQuizScoresByCourseIdAndStudentId(courseId,studentId);
        List<AssignmentFeedback>assignmentFeedbacks = progressService.getAssignmentFeedbacksByCourseAndStudent(courseId, studentId);
        List<Attendance>attendances = progressService.getAttendanceRecordsPerStudent(courseId, studentId);
        for (QuizFeedback quizFeedback : quizScores) {
            score += quizFeedback.getGrade();
            maxScore += quizFeedback.getMaxGrade();
        }

        for(AssignmentFeedback assignmentFeedback : assignmentFeedbacks) {
            score += assignmentFeedback.getGrade();
            maxScore += assignmentFeedback.getMaxGrade();
        }

        score += attendances.size();
        maxScore = lessonService.getAll(courseId).size();
        return score/maxScore;
    }

    public int[] getStudentsTotalGrades(Long courseId) {
        List<Student> students = enrollmentService.getByCourseId(courseId);
        int[] totalGrades = new int[11];

        for (Student student : students) {
            double score = getStudentScores(student, courseId) * 100.0;
            int index = (score <= 0) ? 0 : Math.min((int) (score / 10) + 1, 10);
            totalGrades[index]++;
        }
        return totalGrades;
    }

}
