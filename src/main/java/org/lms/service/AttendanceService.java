package org.lms.service;

import org.lms.entity.*;
import org.lms.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private LessonService lessonService;

    public boolean create(Long studentId, Long lessonId) {
        try {
            Attendance attendance = new Attendance();
            attendance.setLesson(lessonService.getById(lessonId));
            attendance.setStudent((Student) appUserService.getById(studentId));
            attendanceRepository.save(attendance);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long studentId, Long courseId) {
        try {
            Lesson lesson = lessonService.getById(courseId);
            Student student = (Student) appUserService.getById(studentId);
            attendanceRepository.deleteById(attendanceRepository.findByStudentAndLesson(student, lesson).getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkStudentId(Long studentId, Long courseId) {
        try {
            Lesson lesson = lessonService.getById(courseId);
            Student student = (Student) appUserService.getById(studentId);
            return attendanceRepository.findByStudentAndLesson(student, lesson) != null;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public List<Student> getByLessonId(Long lessonId){
        Lesson lesson = lessonService.getById(lessonId);
        if (lesson == null) {
            return null;
        }
        List<Student> studentList = new ArrayList<>();
        for (Attendance attendance : attendanceRepository.findAll()) {
            studentList.add(attendance.getStudent());
        }
        return studentList;
    }
}
