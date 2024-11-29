package com.sparta.oceanbackend.domain.keyword.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "keywords")
@Getter
@NoArgsConstructor
public class Keyword {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 10, unique = true)
  private String keyword;

  @Setter
  @Column(nullable = false)
  private Long count;

  @Builder
  public Keyword(String keyword, Long count) {
    this.keyword = keyword;
    this.count = count;
  }
}
