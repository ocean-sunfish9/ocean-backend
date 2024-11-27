package com.sparta.oceanbackend;

import com.sparta.oceanbackend.api.post.dto.response.PostReadResponse;
import com.sparta.oceanbackend.api.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServicePerformanceTest {

    @Autowired
    private PostService postService;

    private String keyword;
    private int pagenumber;
    private int pagesize;

    private static final int ITERATIONS = 100; // 100번 반복

    @BeforeEach
    public void setUp() {
        keyword = "testKey";
        pagenumber = 1;
        pagesize = 10;
    }

    @Test
    public void testSearchPostsPerformance() {
        long totalDuration = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();
            Page<PostReadResponse> result = postService.searchPosts(pagenumber, pagesize, keyword);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // ms로 변환
            totalDuration += duration;
            assertThat(result).isNotNull();
        }
        long averageDuration = totalDuration / ITERATIONS;
        System.out.println("v1 평균 테스트 시간: " + averageDuration + "ms");
    }

    @Test
    public void testSearchPostsInMemoryPerformance() {
        long totalDuration = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();
            Page<PostReadResponse> result = postService.searchPostsInMemory(pagenumber, pagesize, keyword);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // ms로 변환
            totalDuration += duration;
            assertThat(result).isNotNull();
        }
        long averageDuration = totalDuration / ITERATIONS;
        System.out.println("v2 평균 테스트 시간: " + averageDuration + "ms");
    }

    @Test
    public void testSearchPostsRedisPerformance() {
        long totalDuration = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();
            Page<PostReadResponse> result = postService.searchPostsRedis(pagenumber, pagesize, keyword);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // ms로 변환
            totalDuration += duration;
            assertThat(result).isNotNull();
        }
        long averageDuration = totalDuration / ITERATIONS;
        System.out.println("v3 평균 테스트 시간: " + averageDuration + "ms");
    }
}
