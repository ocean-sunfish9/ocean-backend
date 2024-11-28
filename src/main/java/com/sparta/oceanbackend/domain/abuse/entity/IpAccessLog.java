package com.sparta.oceanbackend.domain.abuse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class IpAccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private LocalDateTime accessTime;

    @Builder
    public IpAccessLog(String ipAddress, LocalDateTime accessTime) {
        this.ipAddress = ipAddress;
        this.accessTime = accessTime;
    }
}
