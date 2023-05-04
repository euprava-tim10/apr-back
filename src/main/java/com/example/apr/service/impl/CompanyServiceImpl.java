package com.example.apr.service.impl;

import com.example.apr.dto.CompanyDto;
import com.example.apr.model.Company;
import com.example.apr.repository.CompanyRepository;
import com.example.apr.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> findAll() {
        List<Company> allCompany = companyRepository.findAll();
        List<CompanyDto> all = new ArrayList<>();
        for (Company c : allCompany) {
            all.add(CompanyDto.builder()
                    .name(c.getName())
                    .pib(c.getPib())
                    .pio(c.getPio())
                    .registrationDate(c.getRegistrationDate())
                    .registrationNumber(c.getRegistrationNumber())
                    .status(c.getStatus())
                    .build());
        }
        return all;
    }

    public CompanyDto findById(Long id){
        Optional<Company> companyOpt = companyRepository.findById(id);
        if (companyOpt.isEmpty()) {
            throw new EntityNotFoundException("There is no commpany with id: " + id);
        }
        Company company = companyOpt.get();


        return CompanyDto.builder()
                .name(company.getName())
                .pio(company.getPio())
                .pib(company.getPib())
                .registrationDate(company.getRegistrationDate())
                .registrationNumber(company.getRegistrationNumber())
                .status(company.getStatus())
                .build();
    }
}
