package com.sparta.oceanbackend.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequest {
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String passwordCheck;
}
