package com.sparta.oceanbackend.api.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthResponse {
    private Long id;
    private String name;

    public AuthResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
