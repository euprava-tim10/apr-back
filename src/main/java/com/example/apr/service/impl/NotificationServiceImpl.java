package com.example.apr.service.impl;

import com.example.apr.dto.CompanyDto;
import com.example.apr.dto.JobAdvertisementDto;
import com.example.apr.dto.NotificationDto;
import com.example.apr.exception.BadRequestException;
import com.example.apr.model.Notification;
import com.example.apr.model.User;
import com.example.apr.repository.NotificationRepository;
import com.example.apr.repository.UserRepository;
import com.example.apr.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<NotificationDto> findAllByUser(String username) {
        logger.info("find notifications service");
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no logged in user");
        }
        User user = userOpt.get();
        List<Notification> notifications = notificationRepository.findAllByUser(user);

        List<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationDtos.add(NotificationDto.builder().
                    status(notification.getStatus())
                    .company(CompanyDto.builder()
                            .registrationNumber(notification.getJobAlert().getCompany().getRegistrationNumber())
                            .registrationDate(notification.getJobAlert().getCompany().getRegistrationDate())
                            .PIO(notification.getJobAlert().getCompany().getPio())
                            .PIB(notification.getJobAlert().getCompany().getPib())
                            .name(notification.getJobAlert().getCompany().getName())
                            .status(notification.getJobAlert().getCompany().getStatus())
                            .build())
                    .jobAlert(JobAdvertisementDto.builder()
                            .company(CompanyDto.builder()
                                    .registrationNumber(notification.getJobAlert().getCompany().getRegistrationNumber())
                                    .registrationDate(notification.getJobAlert().getCompany().getRegistrationDate())
                                    .PIO(notification.getJobAlert().getCompany().getPio())
                                    .PIB(notification.getJobAlert().getCompany().getPib())
                                    .name(notification.getJobAlert().getCompany().getName())
                                    .status(notification.getJobAlert().getCompany().getStatus())
                                    .build())
                            .startDate(notification.getJobAlert().getStartDate())
                            .endDate(notification.getJobAlert().getEndDate())
                            .neededEducation(notification.getJobAlert().getNeededEducation())
                            .profession(notification.getJobAlert().getProfession())
                            .build())
                    .user(notification.getUser())
                    .build());
        }
        logger.info("Notifications, {}", notificationDtos);
        return notificationDtos;

    }
}
