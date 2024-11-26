package com.sparta.oceanbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 페이징 처리시에 반환값들을 간단하게 출력하도록 해줌
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@EnableJpaAuditing
@EnableScheduling
public class OceanBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(OceanBackendApplication.class, args);
  }
}
