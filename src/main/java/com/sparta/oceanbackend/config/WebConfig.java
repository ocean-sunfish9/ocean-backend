package com.sparta.oceanbackend.config;

import com.sparta.oceanbackend.api.handler.AbusePreventionInterceptor;
import com.sparta.oceanbackend.api.handler.AuthUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthUserArgumentResolver authUserArgumentResolver;
    private final AbusePreventionInterceptor abusePreventionInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(abusePreventionInterceptor)
                .addPathPatterns("/**"); // 모든 요청에 대해 AbusePreventionInterceptor 를 적용
    }
}
