package com.sparta.oceanbackend.api.abuse.service;

import com.sparta.oceanbackend.domain.abuse.repository.BlacklistedIpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisRateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private final BlacklistedIpRepository blacklistedIpRepository;
    private final BlacklistService blacklistService;

    private static final long MAX_REQUESTS = 10; // 허용하는 최대 요청 수
    private static final long TIME_WINDOW = 1; // 시간 윈도우 (분)

    public boolean isRateLimited(String ipAddress) {
        if (blacklistedIpRepository.findByIpAddress(ipAddress).isPresent()) {
            return true; // 블랙리스트에 있는 IP는 차단
        }

        String key = "request_count:" + ipAddress;
        Long count = redisTemplate.opsForValue().increment(key);

        if (count == null) {
            count = 0L;
        }

        if (count == 1) {
            redisTemplate.expire(key, TIME_WINDOW, TimeUnit.MINUTES); // 첫번째 요청인 경우, 만료시간을 1분으로 설정
        }

        if (count > MAX_REQUESTS) {
            blacklistService.addToBlacklist(ipAddress); // 블랙리스트에 추가
            return true;
        }

        return false;
    }
}
