package com.sparta.oceanbackend.api.post.dto.response;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Categorys category;
    private int commentCount;

    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.commentCount = post.getComments().size();
    }
}
