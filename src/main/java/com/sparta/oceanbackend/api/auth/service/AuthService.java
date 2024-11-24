package com.sparta.oceanbackend.api.auth.service;

import com.sparta.oceanbackend.api.auth.dto.request.LoginRequest;
import com.sparta.oceanbackend.api.auth.dto.request.RegisterRequest;
import com.sparta.oceanbackend.api.auth.dto.response.AuthResponse;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.config.PasswordEncoder;
import com.sparta.oceanbackend.domain.user.entity.User;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse registerUser(RegisterRequest request) {
        validatePasswordMatch(request.getPassword(), request.getPasswordCheck());
        validateNameDuplicate(request.getName());

        User user = createUser(request.getName(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new AuthResponse(savedUser.getId(), savedUser.getName());
    }

    public AuthResponse loginUser(LoginRequest request) {
        User user = findUserByName(request.getName());
        validatePassword(request.getPassword(), user.getPassword());

        return new AuthResponse(user.getId(), user.getName());
    }

    private void validatePasswordMatch(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new ResponseException(ExceptionType.PASSWORD_MISMATCH);
        }
    }

    private void validateNameDuplicate(String name) {
        if (userRepository.findByName(name).isPresent()) {
            throw new ResponseException(ExceptionType.NAME_IN_USE);
        }
    }

    private User findUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new ResponseException(ExceptionType.NAME_MISMATCH));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ResponseException(ExceptionType.PASSWORD_MISMATCH);
        }
    }

    private User createUser(String name, String password) {
        return User.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
