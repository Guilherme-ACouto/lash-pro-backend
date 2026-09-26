package com.bravapro.appointments.application.usecase;

import com.bravapro.appointments.application.command.CreateAppointmentCommand;
import com.bravapro.appointments.application.command.UpdateAppointmentCommand;
import com.bravapro.appointments.domain.exception.AppointmentConflictException;
import com.bravapro.appointments.domain.model.Appointment;
import com.bravapro.appointments.domain.port.in.AppointmentUseCase;
import com.bravapro.appointments.domain.port.out.AppointmentFinancialPort;
import com.bravapro.appointments.domain.port.out.AppointmentRepository;
import com.bravapro.clients.domain.model.Client;
import com.bravapro.clients.domain.port.out.ClientQueryRepository;
import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.services.domain.model.ServiceOffering;
import com.bravapro.services.domain.port.out.ServiceQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentUseCaseImpl implements AppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final ClientQueryRepository clientQueryRepository;
    private final ServiceQueryRepository serviceQueryRepository;
    private final AppointmentFinancialPort financialPort;

    @Override
    public Appointment create(CreateAppointmentCommand command) {
        findClient(command.getClientId()).assertActive();
        findService(command.getServiceId()).assertActive();
        Appointment appointment = Appointment.schedule(command);
        assertNoConflict(appointment);
        return appointmentRepository.save(appointment);
    }

    private Client findClient(UUID clientId) {
        return clientQueryRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado: " + clientId));
    }

    private ServiceOffering findService(UUID serviceId) {
        return serviceQueryRepository.findById(serviceId)
                .orElseThrow(() -> new BusinessException("Serviço não encontrado: " + serviceId));
    }

    private void assertNoConflict(Appointment appointment) {
        boolean conflicts = appointmentRepository.findActiveByDate(appointment.getScheduledDate()).stream()
                .anyMatch(appointment::overlaps);
        if (conflicts) {
            throw new AppointmentConflictException();
        }
    }

    @Override
    public void update(Appointment appointment, UpdateAppointmentCommand command) {
        findClient(command.getClientId());
        findService(command.getServiceId());
        appointment.update(command);
        appointmentRepository.save(appointment);
    }

    @Override
    public void confirm(Appointment appointment) {
        appointment.confirm();
        appointmentRepository.save(appointment);
    }

    @Override
    public void complete(Appointment appointment, String paymentMethod) {
        String clientName = resolveClientName(appointment.getClientId());
        ServiceOffering service = findService(appointment.getServiceId());
        UUID financialEntryId = financialPort.createIncomeEntry(
                appointment.getId(),
                service.getName() + " — " + clientName,
                service.getPrice(),
                appointment.getScheduledDate(),
                paymentMethod);
        appointment.complete(financialEntryId);
        appointmentRepository.save(appointment);
    }

    private String resolveClientName(UUID clientId) {
        if (clientId == null) {
            return "Cliente";
        }
        return clientQueryRepository.findById(clientId).map(Client::getName).orElse("Cliente");
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.cancel();
        appointmentRepository.save(appointment);
    }

    @Override
    public void noShow(Appointment appointment) {
        appointment.noShow();
        appointmentRepository.save(appointment);
    }

}
