package com.sparta.oceanbackend.api.post.scheduler;

import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
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
    if (keys == null || keys.isEmpty()) {
      return;
    }
    RedisSerializer redisSerializerKey = redisTemplate.getKeySerializer();
    RedisSerializer redisSerializerValue = redisTemplate.getValueSerializer();
    List<Object> results =
        // Pipeline 을 사용하여 요청에대한 연결을 1번만 진행
        redisTemplate.executePipelined(
            (RedisCallback<Integer>)
                connection -> {
                  for (String key : keys) {
                    connection.get(redisSerializerKey.serialize(key));
                    connection.del(redisSerializerValue.serialize(key));
                  }
                  return null;
                });
    // 결과 매핑
    Map<Long, Integer> viewCounts = new HashMap<>();
    Iterator<String> keyIterator = keys.iterator();
    int index = 0;
    while (keyIterator.hasNext()) {
      String key = keyIterator.next();
      Object result = results.get(index++);
      if (result != null) {
        Long postId = Long.parseLong(key.substring(REDIS_KEY_PREFIX.length() - 1)); // 키에서 postId 추출
        Integer count = Integer.parseInt(result.toString());
        viewCounts.put(postId, count);
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
