package com.sparta.oceanbackend.api.post.dto.response;

import lombok.Getter;

@Getter
public class PostCreateResponse {
    private final Long id;

    public PostCreateResponse(Long id) {
        this.id = id;
    }
}
