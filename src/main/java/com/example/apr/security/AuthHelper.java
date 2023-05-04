package com.example.apr.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    public static AuthUser authUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof String)
            return null;

        return ((AuthUserDetails) principal).getUser();
    }
}
