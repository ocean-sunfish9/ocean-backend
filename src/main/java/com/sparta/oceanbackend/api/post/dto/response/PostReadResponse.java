package com.sparta.oceanbackend.api.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
