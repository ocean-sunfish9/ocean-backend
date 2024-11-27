package com.sparta.oceanbackend.config;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  @Value("${spring.cache.redis.time-to-live}")
  private Long cacheTtlMinutes;

  @Bean
  // Lettuce 라이브러리 사용
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  @Bean(name = "redisTemplate1")
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    // String key에 대해 StringRedisSerializer 사용
    redisTemplate.setKeySerializer(new StringRedisSerializer());

    // Object 값에 대해 GenericJackson2JsonRedisSerializer 사용 (객체를 JSON 형태로 직렬화)
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

    // Hash의 key와 value에 대해 각각 설정
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    return redisTemplate;
  }

  @Bean(name = "redisTemplate2")
  @Primary
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    // GenericJackson2JsonRedisSerializer 설정
    ObjectMapper genericObjectMapper = new ObjectMapper();
    genericObjectMapper.activateDefaultTyping(
        LaissezFaireSubTypeValidator.instance, // 보안에 더 강한 건 BasicPolymorphicTypeValidator
        ObjectMapper.DefaultTyping.NON_FINAL,
        JsonTypeInfo.As.PROPERTY);

    GenericJackson2JsonRedisSerializer genericSerializer =
        new GenericJackson2JsonRedisSerializer(genericObjectMapper);

    RedisCacheConfiguration genericCacheConfig =
        RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues() // null 값 제외
            .entryTtl(Duration.ofMinutes(cacheTtlMinutes)) // 만료 시간 설정
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(genericSerializer));

    // Jackson2JsonRedisSerializer 설정
    ObjectMapper jacksonObjectMapper = new ObjectMapper();
    jacksonObjectMapper.findAndRegisterModules();
    jacksonObjectMapper.activateDefaultTyping(
        LaissezFaireSubTypeValidator.instance,
        ObjectMapper.DefaultTyping.NON_FINAL,
        JsonTypeInfo.As.PROPERTY);

    Jackson2JsonRedisSerializer<Object> jacksonSerializer =
        new Jackson2JsonRedisSerializer<>(Object.class) {
          // 직접 구현...
          @Override
          public byte[] serialize(Object value) {
            try {
              return jacksonObjectMapper.writeValueAsBytes(value);
            } catch (Exception e) {
              throw new RuntimeException("Serialization error", e);
            }
          }

          @Override
          public Object deserialize(byte[] bytes) {
            if (bytes == null) {
              return null;
            }
            try {
              return jacksonObjectMapper.readValue(bytes, Object.class);
            } catch (Exception e) {
              throw new RuntimeException("Deserialization error", e);
            }
          }
        };

    RedisCacheConfiguration jacksonCacheConfig =
        RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues() // null 값 제외
            .entryTtl(Duration.ofMinutes(cacheTtlMinutes)) // 만료 시간 설정
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer));

    return RedisCacheManager.builder(redisConnectionFactory)
        .withCacheConfiguration("genericCache", genericCacheConfig)
        .withCacheConfiguration("jacksonCache", jacksonCacheConfig)
        .build();
  }
}
