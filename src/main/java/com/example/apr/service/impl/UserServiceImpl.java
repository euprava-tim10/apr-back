package com.example.apr.service.impl;

import com.example.apr.dto.UserDTO;
import com.example.apr.exception.BadRequestException;
import com.example.apr.model.User;
import com.example.apr.repository.UserRepository;
import com.example.apr.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDTO findFirstByUsername(String username) {
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new BadRequestException("There is no user with username: " + username);
        }
        User user = userOpt.get();

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .education(user.getEducation())
                .profession(user.getProfession())
                .companyId(user.getCompany().getId())
                .role(user.getRole())
                .build();
    }
}
