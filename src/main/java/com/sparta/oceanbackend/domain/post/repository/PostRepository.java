package com.sparta.oceanbackend.domain.post.repository;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDslRepository {

  @Modifying
  @Query("UPDATE Post p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.id = :postId")
  void deletePost(Long postId);

  @Query(
      "select p from Post p left join fetch p.comments c left join fetch p.user u where p.category = :category")
  Page<Post> findByCategory(Categorys category, Pageable pageable);

  @Query(
      "select p from Post p left join fetch p.comments c left join fetch p.user u where p.id = :postId")
  Optional<Post> findByPostId(Long postId);

  @Query(
      "select p from Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.user WHERE p.category!='BEST_FORUM' ORDER BY p.count DESC , p.updatedAt DESC limit 10")
  List<Post> findByBestCountTop10();

  @Modifying
  @Query("UPDATE Post p SET p.category = 'BEST_FORUM'")
  void updateBestPost(List<Post> posts);

  @Query(
      "SELECT p FROM Post p "
          + "LEFT JOIN FETCH p.user u "
          + // User를 FETCH JOIN
          "LEFT JOIN FETCH p.comments c "
          + // Comment를 FETCH JOIN
          "WHERE p.category = 'BEST_FORUM' "
          + "ORDER BY p.count DESC , p.createdAt ")
  List<Post> findByAllBest10(Pageable pageable);
}
