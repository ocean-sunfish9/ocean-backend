package com.sparta.oceanbackend.api.user.service;

import com.sparta.oceanbackend.api.user.dto.request.UserRequest;
import com.sparta.oceanbackend.api.user.dto.response.UserResponse;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.config.PasswordEncoder;
import com.sparta.oceanbackend.domain.user.entity.User;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse changePassword(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseException(ExceptionType.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseException(ExceptionType.PASSWORD_MISMATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        userRepository.updatePassword(id, encodedNewPassword);

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setPassword(encodedNewPassword);

        userRepository.save(user);

        return response;
    }
}
