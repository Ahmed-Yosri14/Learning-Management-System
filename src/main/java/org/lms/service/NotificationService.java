package org.lms.service;

import org.lms.entity.User.AppUser;
import org.lms.entity.Enrollment;
import org.lms.entity.Notification;
import org.lms.repository.EnrollmentRepository;
import org.lms.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    EmailService emailService;

    boolean existsById(Long id){
        return notificationRepository.existsById(id);
    }

    public void createToAllEnrolled(Long courseId, String title, String content){
        for (Enrollment enrollment: enrollmentRepository.findAllByCourseId(courseId)) {
            create(enrollment.getStudent().getId(), title, content);
        }
    }

    public boolean create(Long userId, String title, String content){
        try {
            AppUser user = userService.getById(userId);
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTitle(title);
            notification.setContent(content);
            notificationRepository.save(notification);
//            emailService.sendEmail(user.getEmail(), title, content);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long id){
        try {
            assert existsById(id);
            notificationRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public Notification getById(Long userId, Long id){
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null && notification.getUser().getId() != userId) {
            notification = null;
        }
        if (notification != null) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
        return notification;
    }
    public List<Notification> getAllByUserId(Long userId, Boolean OnlyUnread){
        if (OnlyUnread) {
            return notificationRepository.findAllByUserId(userId);
        }
        return notificationRepository.findAllUnreadByUserId(userId);
    }
    public List<Notification> getAll(){
        return notificationRepository.findAll();
    }
}