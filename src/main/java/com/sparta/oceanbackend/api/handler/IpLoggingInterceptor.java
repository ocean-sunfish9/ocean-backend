package com.sparta.oceanbackend.api.handler;

import com.sparta.oceanbackend.domain.abuse.entity.IpAccessLog;
import com.sparta.oceanbackend.domain.abuse.repository.IpAccessLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IpLoggingInterceptor implements HandlerInterceptor {

    private final IpAccessLogRepository ipAccessLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddress = request.getRemoteAddr();
        IpAccessLog log = IpAccessLog.builder()
                        .ipAddress(ipAddress)
                        .accessTime(LocalDateTime.now())
                        .build();

        ipAccessLogRepository.save(log);

        return true;
    }
}
