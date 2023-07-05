package com.example.apr.repository;

import com.example.apr.model.ChangeStatusRequest;
import com.example.apr.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChangeStatusRequestRepository extends JpaRepository<ChangeStatusRequest, Long> {

    List<ChangeStatusRequest> findAllByRequestStatus(NotificationStatus requestStatus);
}
