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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
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
                    .company(new CompanyDto(jobAdvertisement.getCompany().getName(),
                            jobAdvertisement.getCompany().getPio(),
                            jobAdvertisement.getCompany().getPib(),
                            jobAdvertisement.getCompany().getRegistrationNumber(),
                            jobAdvertisement.getCompany().getRegistrationDate(),
                            jobAdvertisement.getCompany().getStatus(),
                            jobAdvertisement.getCompany().getEmployee()
                    ))
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
                .users(saved.getCandidates())
                .profession(saved.getProfession())
                .neededEducation(saved.getNeededEducation())
                .endDate(saved.getEndDate())
                .company(new CompanyDto(saved.getCompany().getName(),
                        saved.getCompany().getPio(),
                        saved.getCompany().getPib(),
                        saved.getCompany().getRegistrationNumber(),
                        saved.getCompany().getRegistrationDate(),
                        saved.getCompany().getStatus(),
                        saved.getCompany().getEmployee()
                ))
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
                    .company(new CompanyDto(jobAdvertisement.getCompany().getName(),
                            jobAdvertisement.getCompany().getPio(),
                            jobAdvertisement.getCompany().getPib(),
                            jobAdvertisement.getCompany().getRegistrationNumber(),
                            jobAdvertisement.getCompany().getRegistrationDate(),
                            jobAdvertisement.getCompany().getStatus(),
                            jobAdvertisement.getCompany().getEmployee()
                    ))
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
        Optional<JobAdvertisement> byId = jobAdvertisementRepository.findById(id);
        if (byId.isEmpty()) {
            throw new BadRequestException("There is no job advertisement with id: " + id);
        }
        JobAdvertisement jobAdvertisement = byId.get();

        return jobAdvertisement;
    }

    @Override
    public JobAdvertisement jobApplication(Long id, String username) {

        JobAdvertisement jobAdvertisement = findById(id);

        Optional<User> firstByUsername = userRepository.findFirstByUsername(username);
        if (firstByUsername.isEmpty()) {
            throw new BadRequestException("There is no user ");
        }
        User user = firstByUsername.get();
        Notification firstByJobAlertAndUser = notificationRepository.findFirstByJobAlertAndUser(jobAdvertisement, user);
        if (firstByJobAlertAndUser != null) {
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
