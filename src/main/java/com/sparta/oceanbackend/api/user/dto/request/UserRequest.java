package com.sparta.oceanbackend.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
