package org.lms.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lms.entity.Attendance;
import org.lms.entity.Course;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.lms.entity.User.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGeneratorService {

    @Autowired
    private ProgressService progressService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private UserService userService;

    public String generateStudentProgressExcel(Long courseId) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student Progress");
        Row header = sheet.createRow(0);
        String[] columns = {"Student ID", "Quiz Scores Percentage", "Assignment Scores Percentage", "Attendance Percentage"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }
        List<Student> students = enrollmentService.getByCourseId(courseId);
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            Long studentId = student.getId();
            row.createCell(0).setCellValue(studentId);
            row.createCell(1).setCellValue(quizGradesTotal(courseId,studentId));
            row.createCell(2).setCellValue(assignmentGradesTotal(courseId,studentId));
            row.createCell(3).setCellValue(attendanceTracker(courseId,studentId));
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        String directoryPath = "src/main/resources/excel_files";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directoryPath + "/StudentProgress_" + courseId + ".xlsx";
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        return filePath;

    }
    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private Double quizGradesTotal(Long courseId,Long studentId){
        List<QuizFeedback>feedbacks = progressService.getQuizScoresByCourseIdAndStudentId(courseId,studentId);
        double total = 0;
        double maxTotal = 0;
        for(QuizFeedback feedback : feedbacks){
            total += feedback.getGrade();
            maxTotal += feedback.getMaxGrade();
        }
        return total/maxTotal*100;
    }

    private Double assignmentGradesTotal(Long courseId,Long studentId){
        List<AssignmentFeedback>feedbacks = progressService.getAssignmentFeedbacksByCourseAndStudent(courseId, studentId);
        double total = 0;
        double maxTotal = 0;
        for(AssignmentFeedback feedback : feedbacks){
            total+= feedback.getGrade();
            maxTotal += feedback.getMaxGrade();
        }
        return total/maxTotal*100;
    }

    private Double attendanceTracker(Long courseId,Long studentId){
        List<Attendance> attendances = progressService.getAttendanceRecordsPerStudent(courseId, studentId);
        double lessonCount = lessonService.getAll(courseId).size();
        double attendanceCount = attendances.size();
        return attendanceCount/lessonCount*100;
    }
}
