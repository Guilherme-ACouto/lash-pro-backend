package com.bravapro.appointments.application.query;

import com.bravapro.appointments.domain.exception.AppointmentNotFoundException;
import com.bravapro.appointments.domain.model.Appointment;
import com.bravapro.appointments.domain.model.AppointmentDetails;
import com.bravapro.appointments.domain.port.in.AppointmentQueryService;
import com.bravapro.appointments.domain.port.out.AppointmentQueryRepository;
import com.bravapro.clients.domain.model.Client;
import com.bravapro.clients.domain.port.out.ClientQueryRepository;
import com.bravapro.services.domain.model.ServiceOffering;
import com.bravapro.services.domain.port.out.ServiceQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentQueryRepository appointmentQueryRepository;
    private final ClientQueryRepository clientQueryRepository;
    private final ServiceQueryRepository serviceQueryRepository;

    @Override
    public AppointmentDetails getById(UUID id) {
        Appointment appointment =
                appointmentQueryRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));

        String clientName = appointment.getClientId() != null
                ? clientQueryRepository.findById(appointment.getClientId()).map(Client::getName).orElse("—")
                : "—";

        BigDecimal servicePrice = serviceQueryRepository
                .findById(appointment.getServiceId())
                .map(ServiceOffering::getPrice)
                .orElse(BigDecimal.ZERO);
        String serviceName = serviceQueryRepository
                .findById(appointment.getServiceId())
                .map(ServiceOffering::getName)
                .orElse("—");

        return new AppointmentDetails(
                appointment.getId(),
                appointment.getClientId(),
                clientName,
                appointment.getServiceId(),
                serviceName,
                servicePrice,
                appointment.getScheduledDate().toString(),
                appointment.getScheduledTime().toString(),
                appointment.getDurationMinutes(),
                appointment.getStatus().name(),
                appointment.getNotes(),
                appointment.getFinancialEntryId() != null ? appointment.getFinancialEntryId().toString() : null,
                appointment.getCreatedAt().toString());
    }

    @Override
    public List<AppointmentDetails> listByDate(LocalDate date) {
        return appointmentQueryRepository.findByDateWithDetails(date);
    }

    @Override
    public List<AppointmentDetails> listByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentQueryRepository.findByDateRangeWithDetails(startDate, endDate);
    }
}
