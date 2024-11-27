//package com.sparta.oceanbackend;
//
//import com.sparta.oceanbackend.api.hotkeyword.service.HotKeywordService;
//import com.sparta.oceanbackend.api.hotkeyword.dto.HotKeywordReadResponse;
//import com.sparta.oceanbackend.domain.hotkeyword.entity.HotKeyword;
//import com.sparta.oceanbackend.domain.hotkeyword.repository.HotKeywordRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class HotKeywordServicePerformanceTest {
//
//    @Autowired
//    private HotKeywordService hotKeywordService;
//
//    private String keyword;
//
//    private static final int ITERATIONS = 1; // n번 반복으로 바꿔 쓰세용
//    @Autowired
//    private RedisTemplate<Object, Object> redisTemplate;
//    @Autowired
//    private HotKeywordRepository hotKeywordRepository;
//
//    @BeforeEach
//    public void setUp() {
//        hotKeywordRepository.save(new HotKeyword("testKey"));
//
//        hotKeywordService.getHotKeywordRedis(keyword);
//    }
//
//    @Test
//    public void testGetHotKeywordPerformance() {
//        long totalDuration = 0;
//        for (int i = 0; i < ITERATIONS; i++) {
//            long startTime = System.nanoTime();
//            List<HotKeywordReadResponse> result = hotKeywordService.getHotKeyword();
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime) / 1000000; // ms로 변환
//            totalDuration += duration;
//            assertThat(result).isNotNull();
//            assertThat(result).isNotEmpty();
//        }
//        long averageDuration = totalDuration / ITERATIONS;
//        System.out.println("v1 평균 테스트 시간: " + averageDuration + "ms");
//    }
//
//    @Test
//    public void testGetHotKeywordInMemoryPerformance() {
//        long totalDuration = 0;
//        for (int i = 0; i < ITERATIONS; i++) {
//            long startTime = System.nanoTime();
//            List<HotKeywordReadResponse> result = hotKeywordService.getHotKeywordInMemory(keyword);
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime) / 1000000; // ms로 변환
//            totalDuration += duration;
//            assertThat(result).isNotNull();
//        }
//        long averageDuration = totalDuration / ITERATIONS;
//        System.out.println("v2 평균 테스트 시간: " + averageDuration + "ms");
//    }
//
//    @Test
//    public void testGetHotKeywordRedisPerformance() {
//        long totalDuration = 0;
//        for (int i = 0; i < ITERATIONS; i++) {
//            long startTime = System.nanoTime();
//            List<HotKeywordReadResponse> result = hotKeywordService.getHotKeywordRedis(keyword);
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime) / 1000000; // ms로 변환
//            totalDuration += duration;
//            assertThat(result).isNotNull();
//        }
//        long averageDuration = totalDuration / ITERATIONS;
//        System.out.println("v3 평균 테스트 시간: " + averageDuration + "ms");
//    }
//}
