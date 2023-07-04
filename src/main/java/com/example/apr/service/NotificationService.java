package com.example.apr.service;

import com.example.apr.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> findAllByUser(String username);

    List<NotificationDto> findAllByJobAlertId(Long id);

    NotificationDto findFirstByJobAlertIdAndUserId(Long jobAdvertisementId, Long userId);

    NotificationDto acceptApplication(Long jobAdvertisementId, Long userId);

    NotificationDto rejectApplication(Long jobAdvertisementId, Long userId);
}
