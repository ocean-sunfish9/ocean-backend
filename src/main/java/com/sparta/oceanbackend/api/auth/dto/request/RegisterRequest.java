package com.sparta.oceanbackend.api.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String password;
    private String passwordCheck;
}
