package com.sparta.oceanbackend.api.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequest {
    @Size(max = 255)
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
}
