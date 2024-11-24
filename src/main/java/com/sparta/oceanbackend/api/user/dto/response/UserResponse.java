package com.sparta.oceanbackend.api.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;

    @JsonIgnore
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
