package com.example.apr.dto;

import com.example.apr.model.Education;
import com.example.apr.model.Profession;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String username;

    private String firstName;

    private String lastName;

    private String role;

    private Education education;

    private Profession profession;

    private Long companyId;

    private List<UserDTO> employee;

}
