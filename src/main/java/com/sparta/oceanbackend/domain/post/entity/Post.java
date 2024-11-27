package com.sparta.oceanbackend.domain.post.entity;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.common.entity.Timestamped;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.domain.comment.entity.Comment;
import com.sparta.oceanbackend.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
@DynamicInsert // insert 시에 null 값 제외해서 count 값 지정해둔 default 값으로 들어가게끔
@Where(clause = "deleted_at IS NULL")
public class Post extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  private Categorys category;

  @ColumnDefault("1") // default 값 1으로 지정
  @Setter
  private Long count;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Comment> comments;

  @Builder
  public Post(String title, String content, Categorys category, User user) {
    this.title = title;
    this.content = content;
    this.category = category;
    this.user = user;
  }

  public void modifyPost(String title, String content, String category) {
    this.title = title;
    this.content = content;
    this.category = Categorys.valueOf(category);
  }

  public void updateCount() {
    this.count = this.count + 1;
  }

  public void validateActionAllowedForBestPost(){
    if(this.category==Categorys.BEST_FORUM){
      throw new ResponseException(ExceptionType.NOT_ACTION_ALL_ALLOWED_BEST_POST);
    }
  }
}
