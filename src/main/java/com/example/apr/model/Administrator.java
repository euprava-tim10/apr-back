package com.example.apr.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Administrator extends User {


    public Administrator(Long id, String username, String firstName, String lastName, String role, String password, Education education,
                         Profession profession, Company company, Set<JobAdvertisement> jobAdvertisements) {
        super(id, username, firstName, lastName, role, password, education, profession, company, jobAdvertisements);
    }
}
