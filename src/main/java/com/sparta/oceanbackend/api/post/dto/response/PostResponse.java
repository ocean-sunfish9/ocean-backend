package com.sparta.oceanbackend.api.post.dto.response;

import com.sparta.oceanbackend.api.comment.dto.response.CommentResponse;
import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.domain.post.entity.Post;
import java.util.List;
import lombok.Getter;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final Categorys category;
    private final List<CommentResponse> commentList;

    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getUser().getName();
        this.category = post.getCategory();
        this.commentList = post.getComments().stream().map(CommentResponse::new).toList();
    }
}
