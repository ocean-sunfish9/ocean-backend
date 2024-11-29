package com.sparta.oceanbackend.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotBlank(message = "계정명은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String oldPassword;
    @NotBlank(message = "새 비밀번호는 필수 입력 값입니다.")
    private String newPassword;
}
