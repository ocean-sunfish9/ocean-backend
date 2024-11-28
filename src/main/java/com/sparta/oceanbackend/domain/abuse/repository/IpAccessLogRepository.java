package com.sparta.oceanbackend.domain.abuse.repository;

import com.sparta.oceanbackend.domain.abuse.entity.IpAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpAccessLogRepository extends JpaRepository<IpAccessLog, Long> {
}
