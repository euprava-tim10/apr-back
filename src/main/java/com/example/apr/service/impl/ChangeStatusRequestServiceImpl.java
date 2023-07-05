package com.example.apr.service.impl;

import com.example.apr.dto.CreateChangeStatusRequestDto;
import com.example.apr.exception.BadRequestException;
import com.example.apr.model.ChangeStatusRequest;
import com.example.apr.model.Company;
import com.example.apr.model.NotificationStatus;
import com.example.apr.model.User;
import com.example.apr.repository.ChangeStatusRequestRepository;
import com.example.apr.repository.CompanyRepository;
import com.example.apr.repository.UserRepository;
import com.example.apr.security.AuthHelper;
import com.example.apr.service.ChangeStatusRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChangeStatusRequestServiceImpl implements ChangeStatusRequestService {
    public final ChangeStatusRequestRepository changeStatusRequestRepository;
    public final CompanyRepository companyRepository;
    public final UserRepository userRepository;

    ChangeStatusRequestServiceImpl(ChangeStatusRequestRepository changeStatusRequestRepository,
                                   CompanyRepository companyRepository,
                                   UserRepository userRepository) {
        this.changeStatusRequestRepository = changeStatusRequestRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ChangeStatusRequest> findAll() {
        return changeStatusRequestRepository.findAll();
    }

    @Override
    public List<ChangeStatusRequest> findAllByRequestStatus() {
        return changeStatusRequestRepository.findAllByRequestStatus(NotificationStatus.PENDING);
    }

    @Override
    public ChangeStatusRequest createChangeStatusRequest(CreateChangeStatusRequestDto createChangeStatusRequestDto) {
        Optional<User> userOpt = userRepository.findFirstByUsername(AuthHelper.authUser().getUsername());
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no user");
        }
        User user = userOpt.get();
        if (user.getCompany().getId().equals(createChangeStatusRequestDto.getCompanyId())) {

            return changeStatusRequestRepository.save(ChangeStatusRequest.builder()
                    .requestStatus(NotificationStatus.PENDING)
                    .companyStatus(createChangeStatusRequestDto.getCompanyStatus())
                    .companyId(createChangeStatusRequestDto.getCompanyId())
                    .reason(createChangeStatusRequestDto.getReason())
                    .build());
        }
        return null;

    }

    @Override
    public ChangeStatusRequest acceptChangeStatusRequest(Long id) {
        Optional<ChangeStatusRequest> changeStatusRequestRepositoryById = changeStatusRequestRepository.findById(id);

        if (changeStatusRequestRepositoryById.isEmpty()) {
            throw new BadRequestException("There is no ChangeStatusRequest ");
        }

        ChangeStatusRequest changeStatusRequest = changeStatusRequestRepositoryById.get();
        Optional<Company> companyOpt = companyRepository.findById(changeStatusRequest.getCompanyId());

        if (companyOpt.isEmpty()) {
            throw new BadRequestException("There is no company");
        }
        Company company = companyOpt.get();
        company.setStatus(changeStatusRequestRepositoryById.get().getCompanyStatus());
        companyRepository.save(company);

        changeStatusRequest.setRequestStatus(NotificationStatus.ACCEPTED);


        return changeStatusRequestRepository.save(changeStatusRequest);
    }

    @Override
    public ChangeStatusRequest rejectChangeStatusRequest(Long id) {
        Optional<ChangeStatusRequest> changeStatusRequestRepositoryById = changeStatusRequestRepository.findById(id);

        if (changeStatusRequestRepositoryById.isEmpty()) {
            throw new BadRequestException("There is no ChangeStatusRequest ");
        }
        ChangeStatusRequest changeStatusRequest = changeStatusRequestRepositoryById.get();
        changeStatusRequest.setRequestStatus(NotificationStatus.REJECTED);

        return changeStatusRequestRepository.save(changeStatusRequest);
    }
}
