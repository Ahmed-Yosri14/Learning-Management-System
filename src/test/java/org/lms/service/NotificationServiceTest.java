package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Enrollment;
import org.lms.entity.Notification;
import org.lms.entity.User.AppUser;
import org.lms.entity.User.Student;
import org.lms.repository.EnrollmentRepository;
import org.lms.repository.NotificationRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserService userService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationService notificationService;

    private AppUser user;
    private Notification notification;
    private Enrollment enrollment;
    private Student student;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        student = new Student();
        student.setId(2L);
        student.setEmail("student@test.com");
        student.setFirstName("Jane");
        student.setLastName("Smith");

        notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Test Notification");
        notification.setContent("Test Content");
        notification.setIsRead(false);
        notification.setUser(user);

        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        when(notificationRepository.existsById(1L)).thenReturn(true);

        boolean result = notificationService.existsById(1L);

        assertTrue(result);
    }

    @Test
    void createToAllEnrolled_ShouldCreateNotificationsForAllStudents() {
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        when(enrollmentRepository.findAllByCourseId(1L)).thenReturn(enrollments);
        when(userService.getById(2L)).thenReturn(student);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        notificationService.createToAllEnrolled(1L, "Test Title", "Test Content");

        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(userService.getById(1L)).thenReturn(user);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        boolean result = notificationService.create(1L, "Test Title", "Test Content");

        assertTrue(result);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccessful() {
        when(notificationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(notificationRepository).deleteById(1L);

        boolean result = notificationService.delete(1L);

        assertTrue(result);
        verify(notificationRepository).deleteById(1L);
    }

    @Test
    void getById_ShouldReturnAndMarkAsRead_WhenExists() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Notification result = notificationService.getById(1L, 1L);

        assertNotNull(result);
        assertTrue(result.getIsRead());
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void getAll_ShouldReturnAllNotifications() {
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<Notification> result = notificationService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(userService.getById(1L)).thenThrow(new RuntimeException());

        boolean result = notificationService.create(1L, "Test Title", "Test Content");

        assertFalse(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        when(notificationRepository.existsById(1L)).thenThrow(new RuntimeException());

        boolean result = notificationService.delete(1L);

        assertFalse(result);
    }

    @Test
    void getById_ShouldReturnNull_WhenUserIdDoesNotMatch() {
        notification.setUser(student);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getById(1L, 1L);

        assertNull(result);
    }
    @Test
    void getAllByUserId_ShouldReturnAllNotifications_WhenOnlyUnreadIsFalse() {
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationRepository.findAllUnreadByUserId(1L)).thenReturn(notifications);

        List<Notification> result = notificationService.getAllByUserId(1L, false);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllByUserId_ShouldReturnUnreadNotifications_WhenOnlyUnreadIsTrue() {
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationRepository.findAllByUserId(1L)).thenReturn(notifications);

        List<Notification> result = notificationService.getAllByUserId(1L, true);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}