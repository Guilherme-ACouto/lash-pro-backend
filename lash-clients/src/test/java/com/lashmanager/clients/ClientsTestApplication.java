package com.lashmanager.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.lashmanager.clients", "com.lashmanager.core"})
@EnableJpaRepositories(basePackages = {"com.lashmanager.clients", "com.lashmanager.core"})
@EntityScan(basePackages = {"com.lashmanager.clients", "com.lashmanager.core"})
public class ClientsTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientsTestApplication.class, args);
    }
}
