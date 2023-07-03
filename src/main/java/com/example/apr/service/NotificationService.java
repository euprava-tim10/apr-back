package com.example.apr.service;

import com.example.apr.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> findAllByUser(String username);
}
