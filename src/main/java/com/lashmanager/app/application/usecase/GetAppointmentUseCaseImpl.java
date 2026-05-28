package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.AppointmentNotFoundException;
import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.model.Service;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.app.domain.port.in.GetAppointmentUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import com.lashmanager.app.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetAppointmentUseCaseImpl implements GetAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentUseCaseMapper mapper;

    @Override
    public CreateAppointmentUseCase.AppointmentResult execute(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        Optional<Client> clientOpt = clientRepository.findById(appointment.getClientId());
        String clientName = clientOpt.map(Client::getName).orElse("(removido)");

        Optional<Service> serviceOpt = serviceRepository.findById(appointment.getServiceId());
        String serviceName = serviceOpt.map(Service::getName).orElse("(removido)");
        BigDecimal servicePrice = serviceOpt.map(Service::getPrice).orElse(BigDecimal.ZERO);

        return mapper.toResult(appointment, clientName, serviceName, servicePrice);
    }
}
