package com.sparta.oceanbackend.api.user.controller;

import com.sparta.oceanbackend.api.user.dto.request.UserRequest;
import com.sparta.oceanbackend.api.user.dto.response.UserResponse;
import com.sparta.oceanbackend.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("{id}/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody UserRequest request) {
        UserResponse response = userService.changePassword(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
