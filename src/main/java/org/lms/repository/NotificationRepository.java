package org.lms.repository;

import org.lms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT l FROM Notification l WHERE l.user.id = :userId")
    List<Notification> findAllByUserId(Long userId);
}

