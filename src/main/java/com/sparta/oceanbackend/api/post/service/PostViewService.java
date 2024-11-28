package com.sparta.oceanbackend.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostViewService {
  @Qualifier("redisTemplateView")
  private final RedisTemplate<String, Integer> redisTemplate;

  private static final String REDIS_KEY_PREFIX = "post:view:";

  public void incrementViewCount(Long postId) {
    String key = REDIS_KEY_PREFIX + postId;
    redisTemplate.opsForValue().increment(key);
  }
}
