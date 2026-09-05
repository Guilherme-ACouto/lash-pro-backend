package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.application.command.CreateClientCommand;
import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientUseCase;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import com.lashmanager.clients.domain.port.out.ClientRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientUseCaseImpl implements ClientUseCase {

    private final ClientRepository clientRepository;
    private final ClientAppointmentPort clientAppointmentPort;

    @Override
    public Client create(CreateClientCommand command) {
        if (clientRepository.existsByPhone(command.getPhone())) {
            throw new ClientAlreadyExistsException(command.getPhone());
        }

        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .phone(command.getPhone())
                .email(command.getEmail())
                .birthDate(command.getBirthDate())
                .notes(command.getNotes())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return clientRepository.save(client);
    }

    @Override
    public void update(Client client, UpdateClientCommand command) {
        if (clientRepository.existsByPhoneAndIdNot(command.getPhone(), client.getId())) {
            throw new ClientAlreadyExistsException(command.getPhone());
        }

        client.update(command);
        clientRepository.save(client);
    }

    @Override
    public void delete(Client client) {
        List<AppointmentSummary> futureAppointments =
                clientAppointmentPort.findFutureActiveByClientId(client.getId(), LocalDate.now());

        if (!futureAppointments.isEmpty()) {
            throw new HasFutureAppointmentsException("cliente", futureAppointments);
        }

        clientAppointmentPort.unlinkClientFromPastAppointments(client.getId(), LocalDate.now());
        clientRepository.deleteById(client.getId());
    }

    @Override
    public void deactivate(Client client, boolean force) {
        if (!force) {
            List<AppointmentSummary> futureAppointments =
                    clientAppointmentPort.findFutureActiveByClientId(client.getId(), LocalDate.now());
            if (!futureAppointments.isEmpty()) {
                throw new HasFutureAppointmentsException("cliente", futureAppointments);
            }
        } else {
            clientAppointmentPort.deleteFutureAppointmentsByClientId(client.getId(), LocalDate.now());
        }

        client.deactivate();
        clientRepository.save(client);
    }

    @Override
    public void reactivate(Client client) {
        client.reactivate();
        clientRepository.save(client);
    }
}
