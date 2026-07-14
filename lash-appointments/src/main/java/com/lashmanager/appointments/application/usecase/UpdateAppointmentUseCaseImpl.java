package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.exception.AppointmentNotFoundException;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.in.UpdateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.core.domain.exception.BusinessException;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public CreateAppointmentUseCase.AppointmentResult execute(UUID id, UpdateAppointmentCommand command) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        Client client = clientRepository.findById(command.clientId())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado: " + command.clientId()));

        ServiceOffering service = serviceRepository.findById(command.serviceId())
                .orElseThrow(() -> new BusinessException("Serviço não encontrado: " + command.serviceId()));

        Appointment updated = existing.toBuilder()
                .clientId(command.clientId())
                .serviceId(command.serviceId())
                .scheduledDate(command.scheduledDate())
                .scheduledTime(command.scheduledTime())
                .durationMinutes(command.durationMinutes())
                .notes(command.notes())
                .updatedAt(LocalDateTime.now())
                .build();

        Appointment saved = appointmentRepository.save(updated);
        return AppointmentUseCaseMapper.toResult(saved, client.getName(), service.getName(), service.getPrice());
    }
}
