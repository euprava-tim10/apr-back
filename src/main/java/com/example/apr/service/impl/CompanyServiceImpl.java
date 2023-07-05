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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    public List<CompanyDto> findAll(String search, String criteria) {
        List<Company> allCompany = companyRepository.findAll();
        List<CompanyDto> all = new ArrayList<>();
        for (Company c : allCompany) {
            all.add(CompanyDto.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .PIB(c.getPib())
                    .PIO(c.getPio())
                    .registrationDate(c.getRegistrationDate())
                    .registrationNumber(c.getRegistrationNumber())
                    .status(c.getStatus())
                    .employee(c.getEmployee())
                    .build());
        }
        if (criteria == null || search == null) {
            return all;
        }
        String[] criterias = criteria.split(",");
        if (criterias.length > 1) {
            return all.stream().
                    filter(c -> c.getName().toUpperCase().contains(search.toUpperCase()) ||
                            c.getRegistrationNumber().contains(search))
                    .collect(Collectors.toList());
        } else if (criterias[0].equals("name")) {
            return all.stream().
                    filter(c -> c.getName().toUpperCase().contains(search.toUpperCase()))
                    .collect(Collectors.toList());
        }
        return all.stream().
                filter(c -> c.getRegistrationNumber().contains(search))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto findByUser() {
        Optional<User> userOpt = userRepository.findFirstByUsername(AuthHelper.authUser().getUsername());
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no user");
        }
        User user = userOpt.get();
        return CompanyDto.builder()
                .id(user.getCompany().getId())
                .status(user.getCompany().getStatus())
                .name(user.getCompany().getName())
                .registrationNumber(user.getCompany().getRegistrationNumber())
                .registrationDate(user.getCompany().getRegistrationDate())
                .PIB(user.getCompany().getPib())
                .PIO(user.getCompany().getPio())
                .employee(user.getCompany().getEmployee())
                .build();
    }


    public CompanyDto findById(Long id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        if (companyOpt.isEmpty()) {
            throw new EntityNotFoundException("There is no commpany with id: " + id);
        }
        Company company = companyOpt.get();


        return CompanyDto.builder()
                .id(company.getId())
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
                        .registrationNumber(System.currentTimeMillis() + "")
                        .build());
        user.setCompany(saved);
        userRepository.save(user);
        return CompanyDto.builder()
                .id(saved.getId())
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
