package com.bravapro.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.bravapro")
@EnableJpaRepositories(basePackages = "com.bravapro")
@EntityScan(basePackages = "com.bravapro")
// PMD.UseUtilityClass pediria construtor privado, mas isso quebra o boot do Spring: o CGLIB
// precisa gerar uma subclasse pra fazer proxy do @Configuration (via @SpringBootApplication) e
// não consegue chamar um construtor privado da superclasse (BeanDefinitionParsingException).
@SuppressWarnings("PMD.UseUtilityClass")
public class BravaProApplication {

    public static void main(String[] args) {
        SpringApplication.run(BravaProApplication.class, args);
    }
}
