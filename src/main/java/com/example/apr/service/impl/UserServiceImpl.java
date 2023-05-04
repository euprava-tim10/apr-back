package com.example.apr.service.impl;

import com.example.apr.repository.UserRepository;
import com.example.apr.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;

    }

}
