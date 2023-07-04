package com.example.apr.controller;

import com.example.apr.dto.NotificationDto;
import com.example.apr.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        logger.info("get notifications user");
        List<NotificationDto> all = notificationService.findAllByUser(username);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/jobAdvertisement/{idJobAdvertisement}")
    public ResponseEntity<List<NotificationDto>> getAllJobAdvertisementApplications(@PathVariable("idJobAdvertisement") Long id) {
        logger.info("get notifications job advertisement");
        List<NotificationDto> all = notificationService.findAllByJobAlertId(id);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @PostMapping("/accept/{userId}/{idJobAdvertisement}")
    public ResponseEntity<Void> acceptApplication(@PathVariable("idJobAdvertisement") Long id, @PathVariable("userId") Long userId) {
        logger.info("accept");
        NotificationDto notificationDto = notificationService.acceptApplication(id, userId);
        logger.info("accept {}", notificationDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reject/{userId}/{idJobAdvertisement}")

    public ResponseEntity<Void> rejectApplication(@PathVariable("idJobAdvertisement") Long id, @PathVariable("userId") Long userId) {
        logger.info("reject");
        NotificationDto notificationDto = notificationService.rejectApplication(id, userId);
        logger.info("reject {}", notificationDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
