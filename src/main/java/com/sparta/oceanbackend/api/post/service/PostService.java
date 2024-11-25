package com.sparta.oceanbackend.api.post.service;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.api.post.dto.request.PostCreateRequest;
import com.sparta.oceanbackend.api.post.dto.response.PostCreateResponse;
import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.post.repository.PostRepository;
import com.sparta.oceanbackend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public PostCreateResponse createPost(PostCreateRequest postCreateRequest, User user) {
    Post post =
        Post.builder()
            .title(postCreateRequest.getTitle())
            .content(postCreateRequest.getContent())
            .category(Categorys.valueOf(postCreateRequest.getCategory()))
            .user(user)
            .build();
    return new PostCreateResponse(postRepository.save(post).getId());
  }
}
