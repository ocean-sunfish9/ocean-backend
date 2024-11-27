package com.sparta.oceanbackend.domain.post.repository;

import com.sparta.oceanbackend.api.post.dto.response.PostReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryDslRepository {
    Page<PostReadResponse> findByKeyword(String keyword, Pageable pageable);
    Page<PostReadResponse> findByKeywordInBest(String keyword, Pageable pageable);
}
