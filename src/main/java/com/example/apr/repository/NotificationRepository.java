package com.example.apr.repository;

import com.example.apr.model.Notification;
import com.example.apr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);

    List<Notification> findAllByJobAlertId(Long id);

    Optional<Notification> findFirstByJobAlertIdAndUserId(Long jobAlertId, Long userId);
}
