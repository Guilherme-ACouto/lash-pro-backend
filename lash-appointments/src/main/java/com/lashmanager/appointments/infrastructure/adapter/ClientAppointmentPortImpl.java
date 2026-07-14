package com.lashmanager.appointments.infrastructure.adapter;

import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientAppointmentPortImpl implements ClientAppointmentPort {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate from) {
        return appointmentRepository.findFutureActiveByClientId(clientId, from);
    }

    @Override
    public void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate from) {
        appointmentRepository.deleteFutureAppointmentsByClientId(clientId, from);
    }

    @Override
    public void unlinkClientFromPastAppointments(UUID clientId, LocalDate from) {
        appointmentRepository.unlinkClientFromPastAppointments(clientId, from);
    }
}
