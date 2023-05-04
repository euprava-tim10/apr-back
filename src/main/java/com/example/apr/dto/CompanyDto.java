package com.example.apr.dto;

import com.example.apr.model.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    private String name;

    private String pio;

    private String pib;

    private String registrationNumber;

    private LocalDate registrationDate;

    private Status status;


}
