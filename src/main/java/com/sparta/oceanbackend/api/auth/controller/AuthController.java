package com.sparta.oceanbackend.api.auth.controller;

import com.sparta.oceanbackend.api.auth.dto.request.LoginRequest;
import com.sparta.oceanbackend.api.auth.dto.request.RegisterRequest;
import com.sparta.oceanbackend.api.auth.dto.response.AuthResponse;
import com.sparta.oceanbackend.api.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse registerUser(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse loginUser(@RequestBody LoginRequest request) {
        return authService.loginUser(request);
    }
}
