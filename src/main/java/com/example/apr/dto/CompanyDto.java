package com.example.apr.dto;

import com.example.apr.model.Status;
import com.example.apr.model.User;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanyDto {

    private String name;

    private String PIO;

    private String PIB;

    private String registrationNumber;

    private LocalDate registrationDate;

    private Status status;

    private Set<User> employee = new HashSet<>();


}
