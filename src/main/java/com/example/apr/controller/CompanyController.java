package com.example.apr.controller;

import com.example.apr.dto.CompanyDto;
import com.example.apr.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/companies")
public class CompanyController {

    private final CompanyService companyService;

    CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAll(@RequestParam(value = "search", required = false) String search) {
        List<CompanyDto> all = companyService.findAll(search);



        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable("id") Long id) {
        CompanyDto companyDto = companyService.findById(id);

        if (companyDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

}

