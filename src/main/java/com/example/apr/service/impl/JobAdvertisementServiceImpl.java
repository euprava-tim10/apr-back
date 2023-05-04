package com.example.apr.service.impl;

import com.example.apr.repository.JobAdvertisementRepository;
import com.example.apr.service.JobAdvertisementService;
import org.springframework.stereotype.Service;

@Service
public class JobAdvertisementServiceImpl implements JobAdvertisementService {
    private final JobAdvertisementRepository jobAdvertisementRepository;

    public JobAdvertisementServiceImpl(JobAdvertisementRepository jobAdvertisementRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
    }
}
