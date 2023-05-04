package com.example.apr.repository;

import com.example.apr.dto.CompanyDto;
import com.example.apr.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
