package com.sparta.oceanbackend.api.user.dto.request;

import lombok.Getter;

@Getter
public class UserRequest {
    private String name;
    private String oldPassword;
    private String newPassword;
}
