package com.example.apr.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String pio;

    @Column
    private String pib;

    @Column
    private String registrationNumber;

    @Column
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<User> employee = new HashSet<>();


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<JobAdvertisement> jobAdvertisements = new HashSet<>();

}
