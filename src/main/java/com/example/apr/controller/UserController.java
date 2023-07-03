package com.example.apr.controller;

import com.example.apr.dto.UserDTO;
import com.example.apr.security.AuthHelper;
import com.example.apr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(userService.findFirstByUsername(AuthHelper.authUser().getUsername()), HttpStatus.OK);
    }


}
