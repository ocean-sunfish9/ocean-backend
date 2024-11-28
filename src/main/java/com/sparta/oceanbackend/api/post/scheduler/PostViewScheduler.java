package com.sparta.oceanbackend.api.post.scheduler;

import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostViewScheduler {
  @Qualifier("redisTemplateView")
  private final RedisTemplate<String, Integer> redisTemplate;

  private final JdbcTemplate jdbcTemplate;

  private static final String REDIS_KEY_PREFIX = "post:view:*";

  // 테스트용
  @Scheduled(fixedDelay = 30000, zone = "Asia/Seoul")
  // 매일 00시 동작
  @Scheduled(cron = "0 0 0 * * ?")
  public void syncViewCount() {
    Set<String> keys = redisTemplate.keys(REDIS_KEY_PREFIX);
    Map<Long, Integer> viewCounts = new HashMap<>();
    if (keys != null) {
      for (String key : keys) {
        Long postId = Long.parseLong(key.substring(REDIS_KEY_PREFIX.length() - 1));
        Integer count = redisTemplate.opsForValue().get(key);

        if (count != null) {
          viewCounts.put(postId, count);
          redisTemplate.delete(key);
        }
      }
    }
    if (!viewCounts.isEmpty()) {
      bulkUpdateViewCount(viewCounts);
    }
  }

  private void bulkUpdateViewCount(Map<Long, Integer> viewCounts) {
    jdbcTemplate.batchUpdate(
        "UPDATE posts SET count = count + ? WHERE id = ?",
        viewCounts.entrySet(),
        viewCounts.size(),
        (ps, entry) -> {
          ps.setInt(1, entry.getValue());
          ps.setLong(2, entry.getKey());
        });
  }
}
