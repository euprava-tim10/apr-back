package com.example.apr.controller;


import com.example.apr.dto.CreateJobAdvertisement;
import com.example.apr.dto.JobAdvertisementDto;
import com.example.apr.model.JobAdvertisement;
import com.example.apr.security.AuthHelper;
import com.example.apr.service.JobAdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jobAdvertisements")
public class JobAdvertisementController {

    private final JobAdvertisementService jobAdvertisementService;

    JobAdvertisementController(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    Logger logger = LoggerFactory.getLogger(JobAdvertisementController.class);

    @GetMapping
    public ResponseEntity<List<JobAdvertisementDto>> getAll() {
        logger.info("get jobAdvertisements");
        List<JobAdvertisementDto> all = jobAdvertisementService.findAll();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAdvertisement> getJobAdvertisement(@PathVariable("id") Long id) {
        JobAdvertisement jobAdvertisement = jobAdvertisementService.findById(id);
        return new ResponseEntity<>(jobAdvertisement, HttpStatus.OK);
    }

    @GetMapping("/company")
    public ResponseEntity<List<JobAdvertisementDto>> getAllCompanyJobAdvertisements() {


        List<JobAdvertisementDto> all = jobAdvertisementService.findAllCompanyJobAdvertisements(AuthHelper.authUser().getUsername());


        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobAdvertisementDto> createJobAdvertisement(@RequestBody @Validated CreateJobAdvertisement newJobAdvertisement) {


        JobAdvertisementDto created = jobAdvertisementService.createJobAdvertisement(newJobAdvertisement, AuthHelper.authUser().getUsername());

        if (created == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    @PostMapping("/{id}/{username}")
    public ResponseEntity<Void> jobApplication(@PathVariable("id") Long id, @PathVariable("username") String username) {
        logger.info("applications");
        jobAdvertisementService.jobApplication(id, username);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
