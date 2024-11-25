package com.sparta.oceanbackend.api.post.controller;

import com.sparta.oceanbackend.api.post.dto.request.PostCreateRequest;
import com.sparta.oceanbackend.api.post.dto.response.PostCreateResponse;
import com.sparta.oceanbackend.api.post.service.PostService;
import com.sparta.oceanbackend.api.user.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreateResponse> createPost(@AuthenticationPrincipal
    UserDetails impl,@Valid @RequestBody PostCreateRequest postCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postCreateRequest, impl.getUsername()));
    }
}
