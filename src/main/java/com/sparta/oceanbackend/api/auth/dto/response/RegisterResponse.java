package com.sparta.oceanbackend.api.auth.dto.response;

import lombok.Getter;

@Getter
public class RegisterResponse {
    private Long id;
    private String name;

    public RegisterResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
