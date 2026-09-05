package com.lashmanager.appointments.application.query;

import com.lashmanager.appointments.domain.exception.AppointmentNotFoundException;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentDetails;
import com.lashmanager.appointments.domain.port.in.AppointmentQueryService;
import com.lashmanager.appointments.domain.port.out.AppointmentQueryRepository;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.out.ServiceRepository;

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
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public AppointmentDetails getById(UUID id) {
        Appointment appointment =
                appointmentQueryRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));

        String clientName = appointment.getClientId() != null
                ? clientRepository.findById(appointment.getClientId()).map(Client::getName).orElse("—")
                : "—";

        BigDecimal servicePrice = serviceRepository
                .findById(appointment.getServiceId())
                .map(ServiceOffering::getPrice)
                .orElse(BigDecimal.ZERO);
        String serviceName = serviceRepository
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
