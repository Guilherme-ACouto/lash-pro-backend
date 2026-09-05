package com.lashmanager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.lashmanager")
@EnableJpaRepositories(basePackages = "com.lashmanager")
@EntityScan(basePackages = "com.lashmanager")
public final class LashManagerApplication {

  private LashManagerApplication() {}

  public static void main(String[] args) {
    SpringApplication.run(LashManagerApplication.class, args);
  }
}
