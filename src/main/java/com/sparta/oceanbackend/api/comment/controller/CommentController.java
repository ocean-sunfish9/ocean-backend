package com.sparta.oceanbackend.api.comment.controller;

import com.sparta.oceanbackend.common.annotation.AuthUser;
import com.sparta.oceanbackend.api.comment.dto.request.CommentRequest;
import com.sparta.oceanbackend.api.comment.dto.response.CommentResponse;
import com.sparta.oceanbackend.api.comment.service.CommentService;
import com.sparta.oceanbackend.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postid}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postid,
            @AuthUser User user,
            @Valid @RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentService.createComment(postid, user, request);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponse);
    }

    @PutMapping("/{commentid}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentid,
            @PathVariable Long postid,
            @AuthUser User user,
            @Valid @RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentService.updateComment(commentid, postid, user, request);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponse);
    }

    @DeleteMapping("/{commentid}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentid,
            @PathVariable Long postid,
            @AuthUser User user) {
        commentService.deleteComment(commentid, postid, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}