package com.sparta.oceanbackend.config.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.redis")
@Configuration
public class RedisInfo {

    private String host;
    private int port;
    private RedisInfo master;
    private List<RedisInfo> slaves;
}