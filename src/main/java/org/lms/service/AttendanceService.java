package org.lms.service;

import org.lms.entity.*;
import org.lms.entity.User.Student;
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

    @Autowired
    private CourseService courseService;

    public boolean tryRecord(Long studentId, Long lessonId, Long courseId, String otp) {
        Lesson lesson = lessonService.getById(lessonId);
        return lesson.getOtp().equals(otp) && create(studentId, lessonId, courseId);
    }

    public boolean create(Long studentId, Long lessonId, Long courseId) {
        try {
            assert lessonService.existsById(lessonId, courseId);

            Lesson lesson = lessonService.getById(lessonId);
            Attendance attendance = new Attendance();
            attendance.setLesson(lesson);
            attendance.setStudent((Student) appUserService.getById(studentId));
            attendanceRepository.save(attendance);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long studentId, Long lessonId, Long courseId) {
        try {
            assert lessonService.existsById(lessonId, courseId);

            Lesson lesson = lessonService.getById(courseId);
            Student student = (Student) appUserService.getById(studentId);
            attendanceRepository.deleteById(attendanceRepository.findByStudentAndLesson(student, lesson).getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkStudentId(Long studentId, Long lessonId, Long courseId) {
        try {
            assert lessonService.existsById(lessonId, courseId);

            Lesson lesson = lessonService.getById(lessonId);
            Student student = (Student) appUserService.getById(studentId);
            return attendanceRepository.findByStudentAndLesson(student, lesson) != null;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public List<Student> getByLessonId(Long lessonId, Long courseId){
        assert lessonService.existsById(lessonId, courseId);

        Lesson lesson = lessonService.getById(lessonId);
        List<Student> studentList = new ArrayList<>();
        for (Attendance attendance : attendanceRepository.findAllByLesson(lesson)) {
            studentList.add(attendance.getStudent());
        }
        return studentList;
    }
}
