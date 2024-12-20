package org.lms.service;

import org.lms.entity.Attendance;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.entity.User.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public String generatePieChart(Long courseId, int []grades, String chartPath, String ChartName)throws IOException{
        DefaultPieDataset dataset = new DefaultPieDataset();
        if(grades == null){return null;}
        for (int i = 0; i < grades.length; i++) {
            if(grades[i] == 0)continue;
            dataset.setValue("From "+ (i*10)+ "% to " +((i+1) * 10) + "%", (grades[i] / (double) grades.length) * 100);
        }
        JFreeChart pieChart = ChartFactory.createPieChart(
                ChartName + courseId,
                dataset,
                true,
                true,
                false
        );
        File chartFile = new File(chartPath);
        chartFile.getParentFile().mkdirs();
        ChartUtils.saveChartAsPNG(chartFile, pieChart, 800, 600);
        return chartPath;
    }

    public String generateAttendancePieChart(Long courseId)throws IOException{
        int[]grades = getStudentsAttendanceRate(courseId);
        return generatePieChart(courseId, grades,"src/main/resources/charts/AttendanceRate_" + courseId + "_PieChart.png","Attendance Percentage Distribution - Course ");

    }

    public String generateScoresPieChart(Long courseId) throws IOException {
        int[]grades = getStudentsTotalGrades(courseId);
        return generatePieChart(courseId, grades,"src/main/resources/charts/CourseScores_" + courseId + "_PieChart.png","Grades Percentage Distribution - Course ");
    }


    private int[] getStudentsAttendanceRate(Long courseId) {
        List<Student> students = enrollmentService.getByCourseId(courseId);
        if(students.isEmpty()) {return null;}
        int[] totalGrades = new int[11];
        for (Student student : students) {
            Long studentId = student.getId();
            List<Attendance>attendances = progressService.getAttendanceRecordsPerStudent(courseId, studentId);
            double attendedLessons = attendances.size();
            double lessonsCount = lessonService.getAll(courseId).size();
            double score = attendedLessons / (lessonsCount > 0 ? lessonsCount : 1) * 100;
            int index = (score <= 0) ? 0 : Math.min((int) (score / 10) + 1, 10);
            totalGrades[index]++;
        }
        return totalGrades;
    }

    public double getStudentScores(Student student, Long courseId){
        Long studentId = student.getId();
        double score = 0;
        double maxScore = 0;
        System.out.println(studentId);
        System.out.println(courseId);
        List<QuizFeedback> quizScores = progressService.getQuizScoresByCourseIdAndStudentId(courseId,studentId);
        List<AssignmentFeedback>assignmentFeedbacks = progressService.getAssignmentFeedbacksByCourseAndStudent(courseId, studentId);
        for (QuizFeedback quizFeedback : quizScores) {
            score += quizFeedback.getGrade();
            maxScore += quizFeedback.getMaxGrade();
        }

        for(AssignmentFeedback assignmentFeedback : assignmentFeedbacks) {
            score += assignmentFeedback.getGrade();
            maxScore += assignmentFeedback.getMaxGrade();
        }
        return score/(maxScore > 0 ? maxScore : 1);
    }

    public int[] getStudentsTotalGrades(Long courseId) {
        List<Student> students = enrollmentService.getByCourseId(courseId);
        if(students.isEmpty()) {return null;}
        int[] totalGrades = new int[11];

        for (Student student : students) {
            double score = getStudentScores(student, courseId) * 100.0;
            int index = (score <= 0) ? 0 : Math.min((int) (score / 10) + 1, 10);
            totalGrades[index]++;
        }
        return totalGrades;
    }

}
