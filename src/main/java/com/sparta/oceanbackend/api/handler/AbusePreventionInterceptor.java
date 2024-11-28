package com.sparta.oceanbackend.api.handler;

import com.sparta.oceanbackend.api.abuse.service.BlacklistService;
import com.sparta.oceanbackend.api.abuse.service.RedisRateLimiterService;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AbusePreventionInterceptor implements HandlerInterceptor {

    private final RedisRateLimiterService rateLimiterService;
    private final BlacklistService blacklistService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();

        if (blacklistService.isBlocked(ipAddress) || rateLimiterService.isRateLimited(ipAddress)) {
            throw new ResponseException(ExceptionType.TOO_MANY_REQUEST);
        }
        return true; // 정상적인 요청 처리 진행
    }
}
