package com.example.apr.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private NotificationStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    private JobAdvertisement jobAlert;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

}
