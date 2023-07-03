package com.example.apr.dto;

import com.example.apr.model.NotificationStatus;
import com.example.apr.model.User;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotificationDto {
    private NotificationStatus status;

    private JobAdvertisementDto jobAlert;

    private User user;
    private CompanyDto company;
}
