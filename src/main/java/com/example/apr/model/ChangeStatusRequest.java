package com.example.apr.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeStatusRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private Status companyStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private NotificationStatus requestStatus;

    @Column
    private Long companyId;

    @Column
    private String reason;

}
