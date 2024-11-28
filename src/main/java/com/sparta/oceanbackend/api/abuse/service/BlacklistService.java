package com.sparta.oceanbackend.api.abuse.service;

import com.sparta.oceanbackend.domain.abuse.entity.BlacklistedIp;
import com.sparta.oceanbackend.domain.abuse.repository.BlacklistedIpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    private final BlacklistedIpRepository blacklistedIpRepository;

    public boolean isBlocked(String ipAddress) {
        return blacklistedIpRepository.findByIpAddress(ipAddress)
                .map(blacklistedIp -> blacklistedIp.getBlockedUntil().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public void addToBlacklist(String ipAddress) {
        BlacklistedIp blacklistedIp = BlacklistedIp.builder()
                                    .ipAddress(ipAddress)
                                    .blockedUntil(LocalDateTime.now().plusMinutes(3)) // 3분 동안 차단
                                    .build();
        blacklistedIpRepository.save(blacklistedIp);
    }

    @Transactional
    public void cleanUpBlackList() {
        List<BlacklistedIp> blacklistedIps = blacklistedIpRepository.findAll();
        for (BlacklistedIp blacklistedIp : blacklistedIps) {
            if (blacklistedIp.getBlockedUntil().isBefore(LocalDateTime.now())) {
                blacklistedIpRepository.delete(blacklistedIp); // 차단 해제
            }
        }
    }
}
