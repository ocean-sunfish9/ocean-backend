package com.sparta.oceanbackend.domain.abuse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class BlacklistedIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private LocalDateTime blockedUntil;

    @Builder
    public BlacklistedIp(String ipAddress, LocalDateTime blockedUntil) {
        this.ipAddress = ipAddress;
        this.blockedUntil = blockedUntil;
    }
}
