package com.example.apr.dto;


import com.example.apr.model.Education;
import com.example.apr.model.Profession;
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
public class JobAdvertisementDto {

    private Long id;
    private CompanyDto company;

    private LocalDate startDate;

    private LocalDate endDate;

    private Education neededEducation;

    private Profession profession;

    private Set<User> users = new HashSet<>();

}




