package org.lms;

import org.lms.entity.Admin;
import org.lms.entity.AppUser;
import org.lms.entity.Course;
import org.lms.entity.Student;
import org.lms.service.CourseService;
import org.lms.service.EnrollmentService;
import org.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationManager {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AppUser)authentication.getPrincipal();
    };
    public Long getCurrentUserId(){
        return getCurrentUser().getId();
    }
    public boolean checkCourseView(Long courseId){
        Course course = courseService.getById(courseId);
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        if (!(user instanceof Student)){
            return checkCourseViewConfidential(courseId);
        }
        return  enrollmentService.checkStudentId(userId, courseId);
    }

    public boolean checkCourseViewConfidential(Long courseId){
        Course course = courseService.getById(courseId);
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        return user instanceof Admin || course.getInstructor().getId() == userId;
    }

    public boolean checkCourseEdit(Long courseId){
        Course course = courseService.getById(courseId);
        Long userId = getCurrentUserId();
        return course.getInstructor().getId() == userId;
    }
    public boolean checkCourseStudent(Long courseId){
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        return user instanceof Student && enrollmentService.checkStudentId(userId, courseId);
    }
}
