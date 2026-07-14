package com.lashmanager.appointments.application.usecase;

import com.lashmanager.appointments.domain.exception.AppointmentNotFoundException;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.in.GetAppointmentUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class GetAppointmentUseCaseImpl implements GetAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public CreateAppointmentUseCase.AppointmentResult execute(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        String clientName = appointment.getClientId() != null
                ? clientRepository.findById(appointment.getClientId())
                        .map(c -> c.getName()).orElse("—")
                : "—";

        BigDecimal servicePrice = serviceRepository.findById(appointment.getServiceId())
                .map(s -> s.getPrice()).orElse(BigDecimal.ZERO);
        String serviceName = serviceRepository.findById(appointment.getServiceId())
                .map(s -> s.getName()).orElse("—");

        return AppointmentUseCaseMapper.toResult(appointment, clientName, serviceName, servicePrice);
    }
}
