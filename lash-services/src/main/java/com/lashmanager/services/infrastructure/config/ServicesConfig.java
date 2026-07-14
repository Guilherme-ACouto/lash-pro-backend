package com.lashmanager.services.infrastructure.config;

import com.lashmanager.services.application.usecase.*;
import com.lashmanager.services.domain.port.in.*;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Bean
    public CreateServiceUseCase createServiceUseCase(ServiceRepository serviceRepository) {
        return new CreateServiceUseCaseImpl(serviceRepository);
    }

    @Bean
    public UpdateServiceUseCase updateServiceUseCase(ServiceRepository serviceRepository) {
        return new UpdateServiceUseCaseImpl(serviceRepository);
    }

    @Bean
    public GetServiceUseCase getServiceUseCase(ServiceRepository serviceRepository) {
        return new GetServiceUseCaseImpl(serviceRepository);
    }

    @Bean
    public ListServicesUseCase listServicesUseCase(ServiceRepository serviceRepository) {
        return new ListServicesUseCaseImpl(serviceRepository);
    }

    @Bean
    public DeleteServiceUseCase deleteServiceUseCase(ServiceRepository serviceRepository) {
        return new DeleteServiceUseCaseImpl(serviceRepository);
    }

    @Bean
    public DeactivateServiceUseCase deactivateServiceUseCase(ServiceRepository serviceRepository) {
        return new DeactivateServiceUseCaseImpl(serviceRepository);
    }
}
