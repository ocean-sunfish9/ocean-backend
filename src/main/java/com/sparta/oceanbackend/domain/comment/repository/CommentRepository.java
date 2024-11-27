package com.sparta.oceanbackend.domain.comment.repository;

import com.sparta.oceanbackend.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
