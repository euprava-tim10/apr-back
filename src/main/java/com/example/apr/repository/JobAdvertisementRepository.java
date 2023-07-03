package com.example.apr.repository;

import com.example.apr.model.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {

    List<JobAdvertisement> findJobAdvertisementByCompanyId(Long companyId);
}
