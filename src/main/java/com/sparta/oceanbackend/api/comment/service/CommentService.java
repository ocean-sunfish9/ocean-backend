package com.sparta.oceanbackend.api.comment.service;

import com.sparta.oceanbackend.api.comment.dto.request.CommentCreateRequest;
import com.sparta.oceanbackend.api.comment.dto.response.CommentResponse;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.domain.comment.entity.Comment;
import com.sparta.oceanbackend.domain.comment.repository.CommentRepository;
import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.post.repository.PostRepository;
import com.sparta.oceanbackend.domain.user.entity.User;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentResponse createComment(Long postId, User user, CommentCreateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post ID"));
        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }
}