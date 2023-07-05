package com.example.apr.service;

import com.example.apr.dto.CreateJobAdvertisement;
import com.example.apr.dto.JobAdvertisementDto;
import com.example.apr.model.JobAdvertisement;

import java.util.List;

public interface JobAdvertisementService {
    List<JobAdvertisementDto> findAll();

    Boolean createJobAdvertisement(CreateJobAdvertisement createJobAdvertisement, String username);

    List<JobAdvertisementDto> findAllCompanyJobAdvertisements(String username);

    JobAdvertisement findById(Long id);

    JobAdvertisement jobApplication(Long id, String username);
}
