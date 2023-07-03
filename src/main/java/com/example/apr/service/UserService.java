package com.example.apr.service;

import com.example.apr.dto.UserDTO;


public interface UserService {
    UserDTO findFirstByUsername(String username);
}
