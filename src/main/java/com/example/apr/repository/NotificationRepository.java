package com.example.apr.repository;

import com.example.apr.model.JobAdvertisement;
import com.example.apr.model.Notification;
import com.example.apr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);

    Notification findFirstByJobAlertAndUser(JobAdvertisement jobAdvertisement, User user);
}
