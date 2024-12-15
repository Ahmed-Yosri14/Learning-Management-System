package org.lms;

import org.lms.entity.Admin;
import org.lms.entity.AppUser;
import org.lms.entity.Course;
import org.lms.entity.Student;
import org.lms.service.CourseService;
import org.lms.service.EnrollmentService;
import org.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationManager {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    public boolean checkCourseView(Long courseId, Long userId){
        Course course = courseService.getById(courseId);
        AppUser user = userService.getById(userId);
        if (!(user instanceof Student)){
            return checkCourseViewConfidential(courseId, userId);
        }
        return  enrollmentService.checkStudentId(userId, courseId);
    }

    public boolean checkCourseViewConfidential(Long courseId, Long userId){
        Course course = courseService.getById(courseId);
        AppUser user = userService.getById(userId);
        return user instanceof Admin || course.getInstructor().getId() == userId;
    }

    public boolean checkCourseEdit(Long courseId, Long userId){
        Course course = courseService.getById(courseId);
        AppUser user = userService.getById(userId);
        return course.getInstructor().getId() == userId;
    }
}
