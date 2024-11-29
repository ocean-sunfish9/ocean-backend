package com.sparta.oceanbackend.domain.keyword.repository;

import com.sparta.oceanbackend.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    // 중복된 Keyword 로 검색시 조회수증가 아니면 키워드 삽입
    @Modifying
    @Query(value = "INSERT INTO keywords (keyword, count) VALUES (:keyword, 1) " +
            "ON DUPLICATE KEY UPDATE count = count + 1", nativeQuery = true)
    void upsertKeyword(@Param("keyword") String keyword);

    // 인기검색어 10개 추출 쿼리
    @Query("SELECT k FROM Keyword k order by k.count DESC limit 10")
    List<Keyword> getHotKeywords();

    @Transactional
    @Modifying
    @Query("DELETE FROM Keyword")
    void deleteAllKeyword();
}

