package com.lashmanager.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * lash-core não depende de nenhum outro módulo, então (ao contrário de
 * lash-clients) não tem acesso a nenhum @SpringBootApplication "de cima" —
 * precisa da própria configuração mínima de teste, restrita ao seu pacote.
 */
@SpringBootApplication(scanBasePackages = "com.lashmanager.core")
@EnableJpaRepositories(basePackages = "com.lashmanager.core")
@EntityScan(basePackages = "com.lashmanager.core")
public class CoreTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreTestApplication.class, args);
    }
}
