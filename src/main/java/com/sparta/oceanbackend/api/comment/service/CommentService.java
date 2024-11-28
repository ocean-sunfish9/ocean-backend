package com.sparta.oceanbackend.api.comment.service;

import com.sparta.oceanbackend.api.comment.dto.request.CommentRequest;
import com.sparta.oceanbackend.api.comment.dto.response.CommentResponse;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.domain.comment.entity.Comment;
import com.sparta.oceanbackend.domain.comment.repository.CommentRepository;
import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.post.repository.PostRepository;
import com.sparta.oceanbackend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(Long postId, User user, CommentRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_POST));
        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    public CommentResponse updateComment(Long commentId, Long postId, User user, CommentRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_POST));
        List<Comment> comments = commentRepository.findAllByPostIdWithFetchJoin(postId);
        Comment comment = comments.stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_COMMENT));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ExceptionType.NOT_WRITER_COMMENT);
        }
        comment.updateContent(request.getContent());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }


    public void deleteComment(Long commentId, Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_POST));
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_COMMENT));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ExceptionType.NOT_WRITER_COMMENT);
        }
        comment.softDelete();
        commentRepository.save(comment);
    }
}