package com.example.apr.service;


import com.example.apr.dto.CompanyDto;


import java.util.List;

public interface CompanyService {
    List<CompanyDto> findAll();
    CompanyDto findById(Long id);
}
