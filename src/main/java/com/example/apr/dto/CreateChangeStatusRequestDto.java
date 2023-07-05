package com.example.apr.dto;

import com.example.apr.model.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateChangeStatusRequestDto {

    private Status companyStatus;

    private Long companyId;

    private String reason;
}
