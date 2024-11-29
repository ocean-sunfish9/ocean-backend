package com.sparta.oceanbackend.domain.abuse.repository;

import com.sparta.oceanbackend.domain.abuse.entity.BlacklistedIp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistedIpRepository extends JpaRepository<BlacklistedIp, Long> {
    Optional<BlacklistedIp> findByIpAddress(String ipAddress);
}
