package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.in.DeactivateClientUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeactivateClientUseCaseImpl implements DeactivateClientUseCase {

    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void deactivate(UUID id, boolean force) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (!force) {
            List<AppointmentSummary> futureAppointments =
                    appointmentRepository.findFutureActiveByClientId(id, LocalDate.now());
            if (!futureAppointments.isEmpty()) {
                throw new HasFutureAppointmentsException("cliente", futureAppointments);
            }
        }

        log.info("Desativando cliente: {} ({})", client.getName(), id);
        clientRepository.save(withActive(client, false));
    }

    @Override
    public void reactivate(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        log.info("Reativando cliente: {} ({})", client.getName(), id);
        clientRepository.save(withActive(client, true));
    }

    private Client withActive(Client client, boolean active) {
        return Client.builder()
                .id(client.getId())
                .name(client.getName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .birthDate(client.getBirthDate())
                .notes(client.getNotes())
                .active(active)
                .createdAt(client.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
