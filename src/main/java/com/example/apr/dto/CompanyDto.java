package com.example.apr.dto;

import com.example.apr.model.Status;
import com.example.apr.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
@ToString
public class CompanyDto {

    private Long id;
    private String name;

    @JsonProperty("PIO")
    private String PIO;
    @JsonProperty("PIB")
    private String PIB;

    private String registrationNumber;

    private LocalDate registrationDate;

    private Status status;

    private Set<User> employee = new HashSet<>();


}
