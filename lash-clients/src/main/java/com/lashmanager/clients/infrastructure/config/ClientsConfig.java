package com.lashmanager.clients.infrastructure.config;

import com.lashmanager.clients.application.usecase.*;
import com.lashmanager.clients.domain.port.in.*;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsConfig {

    @Bean
    public CreateClientUseCase createClientUseCase(ClientRepository clientRepository) {
        return new CreateClientUseCaseImpl(clientRepository);
    }

    @Bean
    public UpdateClientUseCase updateClientUseCase(ClientRepository clientRepository) {
        return new UpdateClientUseCaseImpl(clientRepository);
    }

    @Bean
    public GetClientUseCase getClientUseCase(ClientRepository clientRepository) {
        return new GetClientUseCaseImpl(clientRepository);
    }

    @Bean
    public ListClientsUseCase listClientsUseCase(ClientRepository clientRepository) {
        return new ListClientsUseCaseImpl(clientRepository);
    }

    @Bean
    public DeleteClientUseCase deleteClientUseCase(
            ClientRepository clientRepository,
            ClientAppointmentPort clientAppointmentPort
    ) {
        return new DeleteClientUseCaseImpl(clientRepository, clientAppointmentPort);
    }

    @Bean
    public DeactivateClientUseCase deactivateClientUseCase(
            ClientRepository clientRepository,
            ClientAppointmentPort clientAppointmentPort
    ) {
        return new DeactivateClientUseCaseImpl(clientRepository, clientAppointmentPort);
    }
}
