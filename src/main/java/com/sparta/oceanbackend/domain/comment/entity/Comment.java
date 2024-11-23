package com.sparta.oceanbackend.domain.comment.entity;

import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Builder
  public Comment(String content, Post post, User user) {
    this.content = content;
    this.post = post;
    this.user = user;
  }
}
