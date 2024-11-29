package com.sparta.oceanbackend.api.post.dto.request;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.common.annotation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostModifyRequest {
    @Size(max = 50)
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    @NotNull(message = "카테고리를 선택해주세요")
    @ValidEnum(enumClass = Categorys.class, message = "올바른 카테고리를 선택해주세요")
    private String category;
}
