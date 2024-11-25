package com.sparta.oceanbackend.api.user.controller;

import com.sparta.oceanbackend.api.user.dto.request.ChangePasswordRequest;
import com.sparta.oceanbackend.api.user.dto.request.UserDeleteRequest;
import com.sparta.oceanbackend.api.user.dto.response.UserResponse;
import com.sparta.oceanbackend.api.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        UserResponse response = userService.changePassword(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<UserResponse> deleteUser(@Valid @PathVariable Long id,
                                                   @RequestBody UserDeleteRequest request,
                                                   HttpServletResponse response) {
        UserResponse userResponse = userService.deleteUser(id, request, response);
        return new ResponseEntity<>(userResponse, HttpStatus.NO_CONTENT);
    }
}
