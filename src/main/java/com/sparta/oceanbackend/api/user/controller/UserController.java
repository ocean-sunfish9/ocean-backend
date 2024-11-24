package com.sparta.oceanbackend.api.user.controller;

import com.sparta.oceanbackend.api.user.dto.request.UserRequest;
import com.sparta.oceanbackend.api.user.dto.response.UserResponse;
import com.sparta.oceanbackend.api.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("{id}/password")
    public UserResponse changePassword(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.changePassword(id, request);
    }
}
