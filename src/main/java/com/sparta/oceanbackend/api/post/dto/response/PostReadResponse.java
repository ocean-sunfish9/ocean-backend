package com.sparta.oceanbackend.api.post.dto.response;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReadResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
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
