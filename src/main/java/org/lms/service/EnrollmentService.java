package org.lms.service;

import org.lms.entity.AppUser;
import org.lms.entity.Course;
import org.lms.entity.Enrollment;
import org.lms.entity.Student;
import org.lms.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AppUserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    NotificationService notificationService;

    public boolean create(Long studentId, Long courseId) {
        try {
            Course course = courseService.getById(courseId);
            AppUser user = userService.getById(studentId);
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setStudent((Student)user);
            enrollmentRepository.save(enrollment);
            notificationService.create(
                    studentId,
                    "Enrolled Successfully!",
                    "You have been enrolled successfully to \'" + course.getName() + "\'!"
            );
            notificationService.create(
                    course.getInstructor().getId(),
                    "New Enrollment!",
                    user.getFirstName() + " just enrolled in your course \'" + course.getName() + "\'!"
            );
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long studentId, Long courseId) {
        try {
            Course course = courseService.getById(courseId);
            Student student = (Student)userService.getById(studentId);
            enrollmentRepository.deleteById(enrollmentRepository.findByStudentAndCourse(student, course).getId());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public boolean checkStudentId(Long studentId, Long courseId) {
        try {
            Course course = courseService.getById(courseId);
            Student student = (Student)userService.getById(studentId);
            return enrollmentRepository.findByStudentAndCourse(student, course) != null;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public List<Student> getByCourseId(Long courseId){
        Course course = courseService.getById(courseId);
        if (course == null) {
            return null;
        }
        List<Student> studentList = new ArrayList<>();
        for (Enrollment enrollment: enrollmentRepository.findAllByCourseId(courseId)) {
            studentList.add(enrollment.getStudent());
        }
        return studentList;
    }
}