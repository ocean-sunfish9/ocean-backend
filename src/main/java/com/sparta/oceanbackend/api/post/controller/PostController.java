package com.sparta.oceanbackend.api.post.controller;

import com.sparta.oceanbackend.api.auth.annotation.AuthUser;
import com.sparta.oceanbackend.api.post.dto.request.PostCreateRequest;
import com.sparta.oceanbackend.api.post.dto.request.PostModifyRequest;
import com.sparta.oceanbackend.api.post.dto.response.PostCreateResponse;
import com.sparta.oceanbackend.api.post.service.PostService;
import com.sparta.oceanbackend.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  @PostMapping
  public ResponseEntity<PostCreateResponse> createPost(
      @AuthUser User user, @Valid @RequestBody PostCreateRequest postCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(postService.createPost(postCreateRequest, user));
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Void> modifyPost(@AuthUser User user, @PathVariable Long postId, @Valid @RequestBody PostModifyRequest postModifyRequest) {
    postService.modifyPost(user.getId(), postId, postModifyRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
