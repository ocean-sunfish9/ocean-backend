package com.sparta.oceanbackend.domain.post.repository;

import com.sparta.oceanbackend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
