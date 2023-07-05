package com.example.apr.service;

import com.example.apr.dto.CreateChangeStatusRequestDto;
import com.example.apr.model.ChangeStatusRequest;

import java.util.List;

public interface ChangeStatusRequestService {
    List<ChangeStatusRequest> findAll();

    List<ChangeStatusRequest> findAllByRequestStatus();

    ChangeStatusRequest createChangeStatusRequest(CreateChangeStatusRequestDto createChangeStatusRequestDto);

    ChangeStatusRequest acceptChangeStatusRequest(Long id);

    ChangeStatusRequest rejectChangeStatusRequest(Long id);
}
