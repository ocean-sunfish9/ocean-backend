package com.sparta.oceanbackend.api.post.service;

import com.sparta.oceanbackend.api.post.dto.request.PostCreateRequest;
import com.sparta.oceanbackend.api.post.dto.response.PostCreateResponse;
import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.post.repository.PostRepository;
import com.sparta.oceanbackend.domain.user.entity.User;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostCreateResponse createPost(PostCreateRequest postCreateRequest, String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("유저없어요"));
        Post post = Post.builder().title(postCreateRequest.getTitle()).content(postCreateRequest.getContent()).category(postCreateRequest.getCategory()).user(user).build();
        return new PostCreateResponse(postRepository.save(post).getId());
    }
}
