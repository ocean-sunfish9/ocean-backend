package com.sparta.oceanbackend.config;

import com.sparta.oceanbackend.api.abuse.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final BlacklistService blacklistService;

    @Scheduled(fixedRate = 60000) // 1분 마다 실행
    public void cleanUpBlacklist() {
        blacklistService.cleanUpBlackList();
    }
}
