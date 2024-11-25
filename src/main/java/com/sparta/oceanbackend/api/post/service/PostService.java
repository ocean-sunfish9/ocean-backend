package com.sparta.oceanbackend.api.post.service;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.api.post.dto.request.PostCreateRequest;
import com.sparta.oceanbackend.api.post.dto.request.PostModifyRequest;
import com.sparta.oceanbackend.api.post.dto.response.PostCreateResponse;
import com.sparta.oceanbackend.api.post.dto.response.PostReadResponse;
import com.sparta.oceanbackend.api.post.dto.response.PostResponse;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.domain.keyword.repository.KeywordRepository;
import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.post.repository.PostRepository;
import com.sparta.oceanbackend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final KeywordRepository keywordRepository;

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

  @Transactional
  public Page<PostReadResponse> searchPosts(int pagenumber, int pagesize, String keyword) {
    Pageable pageable = PageRequest.of(pagenumber - 1, pagesize);
    keywordRepository.upsertKeyword(keyword);
    return postRepository.findByKeyword(keyword, pageable);
  }

  @Transactional
  public void modifyPost(Long userId, Long postId, PostModifyRequest postModifyRequest) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_POST));
    if (!post.getUser().getId().equals(userId)) {
      throw new ResponseException(ExceptionType.NOT_WRITER_POST);
    }
    post.modifyPost(
        postModifyRequest.getTitle(),
        postModifyRequest.getContent(),
        postModifyRequest.getCategory());
    postRepository.save(post);
  }

    @Transactional // 명시적
    public void deletePost(
        Long userId,
        Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResponseException(ExceptionType.NON_EXISTENT_POST));
        if(!post.getUser().getId().equals(userId)){
            throw new ResponseException(ExceptionType.NOT_WRITER_POST);
        }
        postRepository.deletePost(postId);
    }

    public Page<PostResponse> findByCategory(
        String category,
        Pageable pageable) {
        return postRepository.findByCategory(Categorys.valueOf(category), pageable).map(PostResponse::new);
    }
}
