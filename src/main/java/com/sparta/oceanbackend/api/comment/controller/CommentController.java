package com.sparta.oceanbackend.api.comment.controller;

import com.sparta.oceanbackend.api.auth.annotation.AuthUser;
import com.sparta.oceanbackend.api.comment.dto.request.CommentCreateRequest;
import com.sparta.oceanbackend.api.comment.dto.response.CommentResponse;
import com.sparta.oceanbackend.api.comment.service.CommentService;
import com.sparta.oceanbackend.domain.comment.entity.Comment;
import com.sparta.oceanbackend.domain.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postid}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postid,
            @AuthUser User user,
            @Valid @RequestBody CommentCreateRequest request) {
        CommentResponse commentResponse = commentService.createComment(postid, user, request);
        return ResponseEntity.ok(commentResponse);
    }
}