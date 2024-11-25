package com.sparta.oceanbackend.domain.post.repository;

import com.sparta.oceanbackend.api.enums.Categorys;
import com.sparta.oceanbackend.api.post.dto.response.PostResponse;
import com.sparta.oceanbackend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long>,PostQueryDslRepository {

    @Modifying
    @Query("UPDATE Post p SET p.deletedAt = CURRENT_TIMESTAMP WHERE p.id = :postId")
    void deletePost(Long postId);

    @Query("select p from Post p left join fetch p.comments c left join fetch p.user u where p.category = :category")
    Page<Post> findByCategory(
        Categorys category,
        Pageable pageable);
}
