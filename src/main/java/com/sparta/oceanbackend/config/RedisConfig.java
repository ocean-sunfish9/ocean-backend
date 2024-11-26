package com.sparta.oceanbackend.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  @Bean
  // Lettuce 라이브러리 사용
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  @Bean
  @Primary
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues() // null 값 제외
        .entryTtl(Duration.ofMinutes(1L)) //  만료시간 1분
        .computePrefixWith(CacheKeyPrefix.simple()) // key 앞에 :: 붙여줌 ex - post::~~
        .serializeKeysWith(
            RedisSerializationContext
                .SerializationPair
                .fromSerializer
                    (
                        new StringRedisSerializer()
                    )
        )
        .serializeValuesWith(
            RedisSerializationContext
                .SerializationPair
                .fromSerializer(
                    new GenericJackson2JsonRedisSerializer()
                )
        );
      return RedisCacheManager
          .RedisCacheManagerBuilder
          .fromConnectionFactory(redisConnectionFactory)
          .cacheDefaults(cacheConfiguration)
          .build();
  }
}
