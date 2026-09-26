package com.bravapro.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.bravapro.clients", "com.bravapro.core"})
@EnableJpaRepositories(basePackages = {"com.bravapro.clients", "com.bravapro.core"})
@EntityScan(basePackages = {"com.bravapro.clients", "com.bravapro.core"})
public class ClientsTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientsTestApplication.class, args);
    }
}
