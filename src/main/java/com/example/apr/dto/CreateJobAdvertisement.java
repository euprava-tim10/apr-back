package com.example.apr.dto;

import com.example.apr.model.Education;
import com.example.apr.model.Profession;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateJobAdvertisement {
    private LocalDate endDate;

    private Education neededEducation;

    private Profession profession;
}
