package com.sparta.oceanbackend.api.auth.controller;

import com.sparta.oceanbackend.api.auth.dto.request.LoginRequest;
import com.sparta.oceanbackend.api.auth.dto.request.RegisterRequest;
import com.sparta.oceanbackend.api.auth.dto.response.AuthResponse;
import com.sparta.oceanbackend.api.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.loginUser(request, response);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
