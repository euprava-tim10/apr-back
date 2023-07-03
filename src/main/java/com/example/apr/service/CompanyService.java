package com.example.apr.service;


import com.example.apr.dto.CompanyDto;
import com.example.apr.dto.CreateCompanyDto;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> findAll(String search);

    CompanyDto findById(Long id);

    CompanyDto createCompany(CreateCompanyDto createCompanyDto);
}
