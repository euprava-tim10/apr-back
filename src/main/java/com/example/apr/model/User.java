package com.example.apr.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.InheritanceType.SINGLE_TABLE;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = SINGLE_TABLE)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String role;


    @Enumerated(EnumType.STRING)
    @Column
    private Education education;

    @Enumerated(EnumType.STRING)
    @Column
    private Profession profession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Company company;

    @ManyToMany(mappedBy = "candidates")
    private Set<JobAdvertisement> jobAdvertisements = new HashSet<>();
}
