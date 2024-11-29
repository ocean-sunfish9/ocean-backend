package com.sparta.oceanbackend.api.post.scheduler;

import com.sparta.oceanbackend.api.post.dto.response.PostReadResponse;
import com.sparta.oceanbackend.domain.post.entity.Post;
import com.sparta.oceanbackend.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BestPostScheduler {

  private final PostRepository postRepository;

  @Qualifier("redisTemplate1")
  private final RedisTemplate<String, Object> redisTemplate;

  @Transactional
  // 테스트 용
  @Scheduled(fixedDelay = 30000, zone = "Asia/Seoul")
  // 00:30
    @Scheduled(cron = "0 30 0 * * ?", zone = "Asia/Seoul")
  public void updateBestPosts() {
    // 페이지 처리된 인기 게시글 조회
    List<Post> cachedPosts = postRepository.findByBestCountTop10();

    // 데이터베이스에서 가져온 게시글을 갱신
    postRepository.updateBestPost(cachedPosts);

    // 인기 게시글이 10개 미만일 경우 추가로 조회
    if (cachedPosts.size() < 10) {
      int remainingCount = 10 - cachedPosts.size();
      Pageable remainingPageable =
          PageRequest.of(0, remainingCount, Sort.by(Sort.Order.desc("count")));
      List<Post> posts = postRepository.findByAllBest10(remainingPageable);
      cachedPosts.addAll(posts);
    }

    // Post 엔티티를 PostReadResponse로 변환
    List<PostReadResponse> todayTop10 =
        cachedPosts.stream()
            .map(
                post ->
                    PostReadResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .name(post.getUser().getName())
                        .updatedAt(post.getUpdatedAt())
                        .commentList((long) post.getComments().size())
                        .build())
            .toList();

    // Redis 캐시 갱신
    redisTemplate.delete("BestPosts:top10");
    for (PostReadResponse post : todayTop10) {
      redisTemplate.opsForList().rightPush("BestPosts:top10", post);
    }
    // 캐시 만료 시간 설정 (24시간)
    redisTemplate.expire("BestPosts:top10", 24, TimeUnit.HOURS);
  }
}