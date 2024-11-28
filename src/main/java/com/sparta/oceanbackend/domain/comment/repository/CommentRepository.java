package com.sparta.oceanbackend.domain.comment.repository;

import com.sparta.oceanbackend.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.post p JOIN FETCH c.user u WHERE p.id = :postId")
    List<Comment> findAllByPostIdWithFetchJoin(Long postId);
}
