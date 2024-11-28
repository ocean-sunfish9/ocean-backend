package com.sparta.oceanbackend.api.post.controller;

import com.sparta.oceanbackend.common.annotation.AuthUser;
import com.sparta.oceanbackend.api.post.dto.request.PostCreateRequest;
import com.sparta.oceanbackend.api.post.dto.request.PostModifyRequest;
import com.sparta.oceanbackend.api.post.dto.request.SearchCategoryRequest;
import com.sparta.oceanbackend.api.post.dto.response.PostCreateResponse;
import com.sparta.oceanbackend.api.post.dto.response.PostReadResponse;
import com.sparta.oceanbackend.api.post.dto.response.PostResponse;
import com.sparta.oceanbackend.api.post.service.PostService;
import com.sparta.oceanbackend.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  @PostMapping
  public ResponseEntity<PostCreateResponse> createPost(
      @AuthUser User user, @Valid @RequestBody PostCreateRequest postCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(postService.createPost(postCreateRequest, user));
  }

  /*
   * 모든 게시판 검색
   * v1 . NonCache
   * v2 . InMemory-Cache
   * v3 . Redis-Cache
   */

  @GetMapping("/search/v1")
  public ResponseEntity<Page<PostReadResponse>> searchPosts(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "1") int pagenumber,
      @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPosts(pagenumber, pagesize, keyword));
  }

  @GetMapping("/search/v2")
  public ResponseEntity<Page<PostReadResponse>> searchPostsInMemory(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "1") int pagenumber,
      @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPostsInMemory(pagenumber, pagesize, keyword));
  }

  @GetMapping("/search/v3")
  public ResponseEntity<Page<PostReadResponse>> searchPostsRedis(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "1") int pagenumber,
      @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPostsRedis(pagenumber, pagesize, keyword));
  }

  /*
   * 인기 게시판 검색
   * v1 . NonCache
   * v2 . InMemory-Cache
   * v3 . Redis-Cache
   */

  @GetMapping("/search/best/v1")
  public ResponseEntity<Page<PostReadResponse>> searchPostsBest(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "1") int pagenumber,
      @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPostsBest(pagenumber, pagesize, keyword));
  }

  @GetMapping("/search/best/v2")
  public ResponseEntity<Page<PostReadResponse>> searchPostsBestInMemory(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "1") int pagenumber,
      @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPostsBestInMemory(pagenumber, pagesize, keyword));
  }

  @GetMapping("/search/best/v3")
  public ResponseEntity<Page<PostReadResponse>> searchPostsBestRedis(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "1") int pagenumber,
      @RequestParam(defaultValue = "10") int pagesize) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.searchPostsBestRedis(pagenumber, pagesize, keyword));
  }

  @GetMapping("/search/todaybests")
  public ResponseEntity<List<PostReadResponse>> searchPostsToday(){
    return ResponseEntity.status(HttpStatus.OK).body(postService.searchPostsToday());
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Void> modifyPost(
      @AuthUser User user,
      @PathVariable Long postId,
      @Valid @RequestBody PostModifyRequest postModifyRequest) {
    postService.modifyPost(user.getId(), postId, postModifyRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@AuthUser User user, @PathVariable Long postId) {
    postService.deletePost(user.getId(), postId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/search/category")
  public ResponseEntity<Page<PostReadResponse>> findByCategory(
      @RequestBody @Valid SearchCategoryRequest categoryRequest,
      @RequestParam(required = false, defaultValue = "1") int pageNum,
      @RequestParam(required = false, defaultValue = "10") int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    return ResponseEntity.ok(postService.findByCategory(categoryRequest.getCategory(), pageable));
  }

  @GetMapping("/search/{postId}")
  public ResponseEntity<PostResponse> findByPostId(@PathVariable Long postId) {
    return ResponseEntity.ok(postService.findByPostId(postId));
  }
}
