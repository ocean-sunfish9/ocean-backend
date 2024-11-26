package com.sparta.oceanbackend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisSpringBootTest {

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @Test
  void testRedisTemplate() {
    ValueOperations<String, String> ops = redisTemplate.opsForValue();
    // 저장
    ops.set("key", "value");
    // 조회
    String value = ops.get("key");
    assertThat(value).isEqualTo("value");
    // 삭제
    redisTemplate.delete("key");
    assertThat(ops.get("key")).isNull();
  }
}
