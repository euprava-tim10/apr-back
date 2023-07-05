package com.example.apr.service.impl;

import com.example.apr.dto.CompanyDto;
import com.example.apr.dto.CreateJobAdvertisement;
import com.example.apr.dto.JobAdvertisementDto;
import com.example.apr.exception.BadRequestException;
import com.example.apr.model.JobAdvertisement;
import com.example.apr.model.Notification;
import com.example.apr.model.NotificationStatus;
import com.example.apr.model.User;
import com.example.apr.repository.JobAdvertisementRepository;
import com.example.apr.repository.NotificationRepository;
import com.example.apr.repository.UserRepository;
import com.example.apr.service.JobAdvertisementService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class JobAdvertisementServiceImpl implements JobAdvertisementService {
    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public JobAdvertisementServiceImpl(JobAdvertisementRepository jobAdvertisementRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<JobAdvertisementDto> findAll() {
        List<JobAdvertisement> allJobAdvertisement = jobAdvertisementRepository.findAll();


        List<JobAdvertisementDto> all = new ArrayList<>();
        for (JobAdvertisement jobAdvertisement : allJobAdvertisement) {
            all.add(JobAdvertisementDto.builder()
                    .company(CompanyDto.builder()
                            .id(jobAdvertisement.getCompany().getId())
                            .name(jobAdvertisement.getCompany().getName())
                            .employee(jobAdvertisement.getCompany().getEmployee())
                            .PIB(jobAdvertisement.getCompany().getPib())
                            .PIO(jobAdvertisement.getCompany().getPio())
                            .status(jobAdvertisement.getCompany().getStatus())
                            .registrationDate(jobAdvertisement.getCompany().getRegistrationDate())
                            .registrationNumber(jobAdvertisement.getCompany().getRegistrationNumber())
                            .build())
                    .startDate(jobAdvertisement.getStartDate())
                    .endDate(jobAdvertisement.getEndDate())
                    .neededEducation(jobAdvertisement.getNeededEducation())
                    .profession(jobAdvertisement.getProfession())
                    .users(jobAdvertisement.getCandidates())
                    .id(jobAdvertisement.getId())
                    .build());
        }
        return all;
    }

    @Override
    public JobAdvertisementDto createJobAdvertisement(CreateJobAdvertisement createJobAdvertisement, String username) {
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no user with username: " + username);
        }
        User user = userOpt.get();
        if (user.getCompany() == null) {
            return null;
        }

        JobAdvertisement saved = jobAdvertisementRepository.save(
                JobAdvertisement.builder()
                        .company(user.getCompany())
                        .startDate(LocalDate.now())
                        .endDate(createJobAdvertisement.getEndDate())
                        .candidates(new HashSet<>())
                        .profession(createJobAdvertisement.getProfession())
                        .neededEducation(createJobAdvertisement.getNeededEducation())
                        .build());


        return JobAdvertisementDto.builder()
                .id(saved.getId())
                .users(saved.getCandidates())
                .profession(saved.getProfession())
                .neededEducation(saved.getNeededEducation())
                .endDate(saved.getEndDate())
                .company(CompanyDto.builder()
                        .id(saved.getCompany().getId())
                        .name(saved.getCompany().getName())
                        .employee(saved.getCompany().getEmployee())
                        .PIB(saved.getCompany().getPib())
                        .PIO(saved.getCompany().getPio())
                        .status(saved.getCompany().getStatus())
                        .registrationDate(saved.getCompany().getRegistrationDate())
                        .registrationNumber(saved.getCompany().getRegistrationNumber())
                        .build())
                .startDate(saved.getStartDate())
                .build();
    }


    @Override
    public List<JobAdvertisementDto> findAllCompanyJobAdvertisements(String username) {
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no user with username: " + username);
        }
        User user = userOpt.get();
        if (user.getCompany() == null) {
            return new ArrayList<>();
        }
        List<JobAdvertisement> allJobAdvertisement = jobAdvertisementRepository
                .findJobAdvertisementByCompanyId(user.getCompany().getId());


        List<JobAdvertisementDto> all = new ArrayList<>();
        for (JobAdvertisement jobAdvertisement : allJobAdvertisement) {
            all.add(JobAdvertisementDto.builder()
                    .id(jobAdvertisement.getId())
                    .company(CompanyDto.builder()
                            .id(jobAdvertisement.getCompany().getId())
                            .name(jobAdvertisement.getCompany().getName())
                            .employee(jobAdvertisement.getCompany().getEmployee())
                            .PIB(jobAdvertisement.getCompany().getPib())
                            .PIO(jobAdvertisement.getCompany().getPio())
                            .status(jobAdvertisement.getCompany().getStatus())
                            .registrationDate(jobAdvertisement.getCompany().getRegistrationDate())
                            .registrationNumber(jobAdvertisement.getCompany().getRegistrationNumber())
                            .build())
                    .startDate(jobAdvertisement.getStartDate())
                    .endDate(jobAdvertisement.getEndDate())
                    .neededEducation(jobAdvertisement.getNeededEducation())
                    .profession(jobAdvertisement.getProfession())
                    .users(jobAdvertisement.getCandidates())
                    .build());
        }
        return all;
    }

    @Override
    public JobAdvertisement findById(Long id) {
        Optional<JobAdvertisement> jobAdvertisementById = jobAdvertisementRepository.findById(id);
        if (jobAdvertisementById.isEmpty()) {
            throw new BadRequestException("There is no job advertisement with id: " + id);
        }

        return jobAdvertisementById.get();
    }

    @Override
    public JobAdvertisement jobApplication(Long id, String username) {

        JobAdvertisement jobAdvertisement = findById(id);

        Optional<User> firstByUsername = userRepository.findFirstByUsername(username);
        if (firstByUsername.isEmpty()) {
            throw new BadRequestException("There is no user ");
        }
        User user = firstByUsername.get();
        Optional<Notification> firstByJobAlertIdAndUserId = notificationRepository.findFirstByJobAlertIdAndUserId(jobAdvertisement.getId(), user.getId());
        if (firstByJobAlertIdAndUserId.isPresent()) {
            return jobAdvertisement;
        }
        Set<User> candidates = jobAdvertisement.getCandidates();
        candidates.add(user);
        jobAdvertisement.setCandidates(candidates);


        jobAdvertisementRepository.save(jobAdvertisement);

        notificationRepository.save(
                Notification.builder()
                        .jobAlert(jobAdvertisement)
                        .user(user)
                        .status(NotificationStatus.PENDING)
                        .build());
        return jobAdvertisement;
    }

}
