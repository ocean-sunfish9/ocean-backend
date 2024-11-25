package com.sparta.oceanbackend.domain.post.repository;

import static io.jsonwebtoken.lang.Strings.hasText;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.oceanbackend.api.post.dto.response.PostReadResponse;
import com.sparta.oceanbackend.domain.comment.entity.QComment;
import com.sparta.oceanbackend.domain.post.entity.QPost;
import com.sparta.oceanbackend.domain.user.entity.QUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  private final QPost post = QPost.post;
  private final QUser user = QUser.user;
  private final QComment comment = QComment.comment;

  @Override
  public Page<PostReadResponse> findByKeyword(String keyword, Pageable pageable) {

    QueryResults<PostReadResponse> results =
        queryFactory
            .select(
                Projections.fields(
                    PostReadResponse.class,
                    post.id,
                    post.title,
                    user.name.as("name"),
                    comment.count().as("commentList"),
                    post.updatedAt))
            .from(post)
            .leftJoin(post.user, user)
            .leftJoin(post.comments, comment)
            .where(hasTitle(keyword).or(hasContent(keyword)).or(hasAuthor(keyword)))
            .groupBy(post.id)
            .orderBy(post.updatedAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();
    List<PostReadResponse> content = results.getResults();
    long total = results.getTotal();
    return new PageImpl<>(content, pageable, total);
  }

  private BooleanExpression hasTitle(String keyword) {
    return hasText(keyword) ? QPost.post.title.containsIgnoreCase(keyword) : null;
  }

  private BooleanExpression hasContent(String keyword) {
    return hasText(keyword) ? QPost.post.content.containsIgnoreCase(keyword) : null;
  }

  private BooleanExpression hasAuthor(String keyword) {
    return hasText(keyword) ? QUser.user.name.containsIgnoreCase(keyword) : null;
  }
}
