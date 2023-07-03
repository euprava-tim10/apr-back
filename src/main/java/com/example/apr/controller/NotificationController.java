package com.example.apr.controller;

import com.example.apr.dto.NotificationDto;
import com.example.apr.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    Logger logger = LoggerFactory.getLogger(NotificationController.class);

    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/{username}")
    public ResponseEntity<List<NotificationDto>> getAllUserApplication(@PathVariable("username") String username) {
        logger.info("get notifications");
        List<NotificationDto> all = notificationService.findAllByUser(username);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
