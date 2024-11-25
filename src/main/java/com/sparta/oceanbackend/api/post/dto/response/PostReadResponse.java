package com.sparta.oceanbackend.api.post.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReadResponse {
  private Long id;
  private String title;
  private String name;
  private Long commentList;
  private LocalDateTime updatedAt;

  @Builder
  public PostReadResponse(
      Long id, String title, String name, Long commentList, LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.name = name;
    this.commentList = commentList;
    this.updatedAt = updatedAt;
  }
}
