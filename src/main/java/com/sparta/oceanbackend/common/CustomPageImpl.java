package com.sparta.oceanbackend.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

// 페이징 데이터 처리를 위한 객체
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"}) // Json 직렬화 무시 할 필드를 지정
public class CustomPageImpl <T> extends PageImpl<T>{

    @JsonCreator(mode = Mode.PROPERTIES) // JSON 에서 이 클래스의 인스턴스를 생성할 때 사용자 생성자를 지정
    public CustomPageImpl(
            @JsonProperty("content") List<T> content,
            @JsonProperty("number")int page,
            @JsonProperty("size")int size,
            @JsonProperty("totalElements")long total) {
        super(content, PageRequest.of(page, size), total);
    }

    public CustomPageImpl(Page<T> page){
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}
