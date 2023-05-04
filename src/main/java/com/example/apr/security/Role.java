package com.example.apr.security;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String authority;

    public Role(String role) {
        authority = role;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
