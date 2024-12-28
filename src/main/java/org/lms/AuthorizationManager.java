package org.lms;

import org.lms.entity.*;
import org.lms.entity.Submission.Submission;
import org.lms.entity.User.Admin;
import org.lms.entity.User.AppUser;
import org.lms.entity.User.Student;
import org.lms.service.CourseService;
import org.lms.service.EnrollmentService;
import org.lms.service.Submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationManager {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private SubmissionService submissionService;

    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AppUser)authentication.getPrincipal();
    };
    public Long getCurrentUserId(){
        return getCurrentUser().getId();
    }
    public boolean hasAccess(Long courseId){
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        if (!(user instanceof Student)){
            return isAdminOrInstructor(courseId);
        }
        return enrollmentService.checkStudentId(userId, courseId);
    }
    public boolean isAdminOrInstructor(Long courseId){
        Course course = courseService.getById(courseId);
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        return user instanceof Admin || course.getInstructor().getId() == userId;
    }
    public boolean isInstructor(Long courseId){
        Course course = courseService.getById(courseId);
        Long userId = getCurrentUserId();
        return course.getInstructor().getId() == userId;
    }
    public boolean isEnrolled(Long courseId){
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        return user instanceof Student && enrollmentService.checkStudentId(userId, courseId);
    }
    public boolean ownsSubmission(Long submissionId){
        Submission submission = submissionService.getById(submissionId);
        AppUser user = getCurrentUser();
        Long userId = user.getId();
        return user instanceof Student
                && submissionService.existsById(submissionId)
                && submission.getStudent().getId().equals(userId);
    }
    public boolean canAccessSubmissionDetails(Long courseId, Long submissionId){
        return isAdminOrInstructor(courseId) || ownsSubmission(submissionId);
    }
}
