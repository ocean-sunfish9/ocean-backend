package com.sparta.oceanbackend.api.post.dto.request;

import com.sparta.oceanbackend.api.enums.Categorys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    @Size(max = 50)
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private Categorys category;
}
