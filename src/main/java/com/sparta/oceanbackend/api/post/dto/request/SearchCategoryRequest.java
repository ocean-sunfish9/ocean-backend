package com.sparta.oceanbackend.api.post.dto.request;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.common.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SearchCategoryRequest {
    @NotNull(message = "카테고리를 선택해주세요")
    @ValidEnum(enumClass = Categorys.class, message = "올바른 카테고리를 선택해주세요")
    private String category;
}
