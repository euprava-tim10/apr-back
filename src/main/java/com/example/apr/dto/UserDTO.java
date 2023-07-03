package com.example.apr.dto;

import com.example.apr.model.Education;
import com.example.apr.model.Profession;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String username;

    private String firstName;

    private String lastName;

    private Education education;

    private Profession profession;

    private Long companyId;


}
