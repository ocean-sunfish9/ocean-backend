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
import org.springframework.cache.annotation.Cacheable;
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
    if (postCreateRequest.getCategory().equals(Categorys.BEST_FORUM.toString())) {
      throw new ResponseException(ExceptionType.NOT_ACTION_ALL_ALLOWED_BEST_POST);
    }
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
  @Cacheable(
      value = "searchPostsCache",
      key = "#keyword + '_' + #pagenumber + '_' + #pagesize",
      condition = "#pagesize < 100",
      cacheManager = "cacheManager")
  public Page<PostReadResponse> searchPostsInMemory(int pagenumber, int pagesize, String keyword) {
    Pageable pageable = PageRequest.of(pagenumber - 1, pagesize);
    keywordRepository.upsertKeyword(keyword);
    return postRepository.findByKeyword(keyword, pageable);
  }

  @Transactional
  @Cacheable(
      cacheNames = "keyword",
      key = "'keyword:' + #keyword + 'pagenumber:' + #pagenumber + 'pagesize:' + #pagesize",
      cacheManager = "redisCacheManager")
  public Page<PostReadResponse> searchPostsRedis(int pagenumber, int pagesize, String keyword) {
    Pageable pageable = PageRequest.of(pagenumber - 1, pagesize);
    keywordRepository.upsertKeyword(keyword);
    return postRepository.findByKeyword(keyword, pageable);
  }

  @Transactional
  public Page<PostReadResponse> searchPostsBest(int pagenumber, int pagesize, String keyword) {
    Pageable pageable = PageRequest.of(pagenumber - 1, pagesize);
    keywordRepository.upsertKeyword(keyword);
    return postRepository.findByKeywordInBest(keyword, pageable);
  }

  @Transactional
  @Cacheable(
      value = "searchPostsBestCache",
      key = "#keyword + '_' + #pagenumber + '_' + #pagesize",
      condition = "#pagesize < 100",
      cacheManager = "cacheManager")
  public Page<PostReadResponse> searchPostsBestInMemory(
      int pagenumber, int pagesize, String keyword) {
    Pageable pageable = PageRequest.of(pagenumber - 1, pagesize);
    keywordRepository.upsertKeyword(keyword);
    return postRepository.findByKeywordInBest(keyword, pageable);
  }

  @Transactional
  @Cacheable(
      cacheNames = "keyword",
      key = "'keyword:' + #keyword + 'pagenumber:' + #pagenumber + 'pagesize:' + #pagesize",
      cacheManager = "redisCacheManager")
  public Page<PostReadResponse> searchPostsBestRedis(int pagenumber, int pagesize, String keyword) {
    Pageable pageable = PageRequest.of(pagenumber - 1, pagesize);
    keywordRepository.upsertKeyword(keyword);
    return postRepository.findByKeywordInBest(keyword, pageable);
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
    post.validateActionAllowedForBestPost();
    post.modifyPost(
        postModifyRequest.getTitle(),
        postModifyRequest.getContent(),
        postModifyRequest.getCategory());
    postRepository.save(post);
  }

  @Transactional // 명시적
  public void deletePost(Long userId, Long postId) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_POST));
    if (!post.getUser().getId().equals(userId)) {
      throw new ResponseException(ExceptionType.NOT_WRITER_POST);
    }
    post.validateActionAllowedForBestPost();
    postRepository.deletePost(postId);
  }

  public Page<PostReadResponse> findByCategory(String category, Pageable pageable) {
    return postRepository
        .findByCategory(Categorys.valueOf(category), pageable)
        .map(
            post ->
                PostReadResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .name(post.getUser().getName())
                    .commentList((long) post.getComments().size())
                    .updatedAt(post.getUpdatedAt())
                    .build());
  }

  @Transactional
  public PostResponse findByPostId(Long postId) {
    Post post =
        postRepository
            .findByPostId(postId)
            .orElseThrow(() -> new ResponseException(ExceptionType.NON_EXISTENT_POST));
    post.updateCount();
    return new PostResponse(post);
  }
}
