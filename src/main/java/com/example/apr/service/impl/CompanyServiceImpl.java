package com.example.apr.service.impl;

import com.example.apr.dto.CompanyDto;
import com.example.apr.dto.CreateCompanyDto;
import com.example.apr.exception.BadRequestException;
import com.example.apr.model.Company;
import com.example.apr.model.Status;
import com.example.apr.model.User;
import com.example.apr.repository.CompanyRepository;
import com.example.apr.repository.UserRepository;
import com.example.apr.security.AuthHelper;
import com.example.apr.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CompanyDto> findAll(String search) {

        List<Company> allCompany = companyRepository.findAll();


        List<CompanyDto> all = new ArrayList<>();
        for (Company c : allCompany) {
            all.add(CompanyDto.builder()
                    .name(c.getName())
                    .PIB(c.getPib())
                    .PIO(c.getPio())
                    .registrationDate(c.getRegistrationDate())
                    .registrationNumber(c.getRegistrationNumber())
                    .status(c.getStatus())
                    .employee(c.getEmployee())
                    .build());
        }
        if (search != null) {
            return all.stream().
                    filter(c -> c.getName().toUpperCase().contains(search.toUpperCase()) ||
                            c.getRegistrationNumber().contains(search))
                    .collect(Collectors.toList());
        }
        return all;
    }


    public CompanyDto findById(Long id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        if (companyOpt.isEmpty()) {
            throw new EntityNotFoundException("There is no commpany with id: " + id);
        }
        Company company = companyOpt.get();


        return CompanyDto.builder()
                .name(company.getName())
                .PIO(company.getPio())
                .PIB(company.getPib())
                .registrationDate(company.getRegistrationDate())
                .registrationNumber(company.getRegistrationNumber())
                .status(company.getStatus())
                .employee(company.getEmployee())
                .build();
    }

    @Override
    public CompanyDto createCompany(CreateCompanyDto createCompanyDto) {
        Optional<User> userOpt = userRepository.findFirstByUsername(AuthHelper.authUser().getUsername());
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no user");
        }
        User user = userOpt.get();

        Company saved = companyRepository.save(
                Company.builder()
                        .name(createCompanyDto.getName())
                        .pib(createCompanyDto.getPib())
                        .pio(createCompanyDto.getPio())
                        .employee(new HashSet<>())
                        .jobAdvertisements(new HashSet<>())
                        .registrationDate(LocalDate.now())
                        .status(Status.ACTIVE)
                        .employee(new HashSet<>())
                        .registrationNumber(String.valueOf(new Random(System.currentTimeMillis())))
                        .build());
        return CompanyDto.builder()
                .name(saved.getName())
                .employee(saved.getEmployee())
                .PIB(saved.getPib())
                .PIO(saved.getPio())
                .status(saved.getStatus())
                .registrationDate(saved.getRegistrationDate())
                .registrationNumber(saved.getRegistrationNumber())
                .build();
    }

}
