package com.sparta.oceanbackend.api.user.service;

import com.sparta.oceanbackend.api.user.dto.request.ChangePasswordRequest;
import com.sparta.oceanbackend.api.user.dto.request.UserDeleteRequest;
import com.sparta.oceanbackend.api.user.dto.response.UserResponse;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.config.PasswordEncoder;
import com.sparta.oceanbackend.domain.user.entity.User;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse changePassword(Long id, ChangePasswordRequest request) {
        User user = getUserById(id);
        validatePassword(request.getOldPassword(), user.getPassword());

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        userRepository.updatePassword(id, encodedNewPassword);

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());

        userRepository.save(user);

        return response;
    }

    public UserResponse deleteUser(Long id, UserDeleteRequest request, HttpServletResponse response) {
        User user = getUserById(id);
        validatePassword(request.getPassword(), user.getPassword());
        validatePasswordMatch(request.getPassword(), request.getPasswordCheck());

        userRepository.deleteUserById(id, user.getName() + "_deleted", "");

        // 쿠키에서 토큰 삭제(로그아웃)
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName() + "_deleted");

        return userResponse;
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseException(ExceptionType.USER_NOT_FOUND));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ResponseException(ExceptionType.PASSWORD_MISMATCH);
        }
    }

    private void validatePasswordMatch(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new ResponseException(ExceptionType.PASSWORD_MISMATCH);
        }
    }
}
