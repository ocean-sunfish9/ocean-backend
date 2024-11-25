package com.sparta.oceanbackend.domain.keyword.repository;

import com.sparta.oceanbackend.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Modifying
    @Query(value = "INSERT INTO keywords (keyword, count) VALUES (:keyword, 1) " +
            "ON DUPLICATE KEY UPDATE count = count + 1", nativeQuery = true)
    void upsertKeyword(@Param("keyword") String keyword);
}

