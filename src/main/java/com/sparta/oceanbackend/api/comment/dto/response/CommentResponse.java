package com.sparta.oceanbackend.api.comment.dto.response;

import com.sparta.oceanbackend.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    Long id;
    String content;
    String name;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getUser().getName();
    }
}
