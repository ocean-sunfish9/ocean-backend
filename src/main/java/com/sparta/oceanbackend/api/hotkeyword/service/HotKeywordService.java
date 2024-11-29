package com.sparta.oceanbackend.api.hotkeyword.service;

import com.sparta.oceanbackend.api.hotkeyword.dto.HotKeywordReadResponse;
import com.sparta.oceanbackend.domain.hotkeyword.entity.HotKeyword;
import com.sparta.oceanbackend.domain.hotkeyword.repository.HotKeywordRepository;
import com.sparta.oceanbackend.domain.keyword.repository.KeywordRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotKeywordService {

  private final KeywordRepository keywordRepository;
  private final HotKeywordRepository hotKeywordRepository;
  private final JdbcTemplate jdbcTemplate;
  private final RedisTemplate<Object, Object> redisTemplate;

    @Transactional
  @Scheduled(fixedDelay = 600000, zone = "Asia/Seoul")
  // 10분 간격으로 keyword Table 상위 10개의 값을 HotKeyword Table 로 삽입 및 삭제
//  @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") // 매일 자정에 실행
  public void updateHotKeyword() {
    hotKeywordRepository.deleteAllHotKeyword();
    redisTemplate.delete("keyword::*"); // Redis 캐시 삭제
    List<HotKeyword> hotKeywords =
        keywordRepository.getHotKeywords().stream()
            // keyword -> HotKeyword 변환 작업
            .map(Keyword -> HotKeyword.builder().keyword(Keyword.getKeyword()).build())
            .toList();
    // 쿼리문을 모아서 한번에 전송
    jdbcTemplate.batchUpdate(
        "INSERT INTO hotkeywords(keyword) values (?)",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            HotKeyword hotKeyword = hotKeywords.get(i);
            ps.setString(1, hotKeyword.getKeyword());
          }

          @Override
          public int getBatchSize() {
            return hotKeywords.size();
          }
        });
    // 키워드 Table 전체 제거
    keywordRepository.deleteAllKeyword();
  }

  @Transactional(readOnly = true)
  public List<HotKeywordReadResponse> getHotKeyword() {
    return convertToHotKeywordReadResponse(hotKeywordRepository.findAll());
  }

  @Transactional(readOnly = true)
  @Cacheable(
          value = "searchPostsCache",
          key = "#hotKeyword",
          cacheManager = "cacheManager",
          condition = "#hotKeyword != null && !#hotKeyword.isEmpty()",
          unless = "#result == null || #result.isEmpty()"
  )
  public List<HotKeywordReadResponse> getHotKeywordInMemory() {
      return convertToHotKeywordReadResponse(hotKeywordRepository.findAll());
  }

  @Transactional(readOnly = true)
  @Cacheable(
          cacheNames = "hotKeyword",
          key = "'hotKeyword:' + #hotKeyword",
          cacheManager = "redisTemplate2"
  )
  public List<HotKeywordReadResponse> getHotKeywordRedis() {
      return convertToHotKeywordReadResponse(hotKeywordRepository.findAll());
  }

  private List<HotKeywordReadResponse> convertToHotKeywordReadResponse(List<HotKeyword> hotKeywords) {
    return hotKeywords.stream()
        .map(
            hotKeyword ->
                HotKeywordReadResponse.builder().keyword(hotKeyword.getKeyword()).build())
        .toList();
  }
}
