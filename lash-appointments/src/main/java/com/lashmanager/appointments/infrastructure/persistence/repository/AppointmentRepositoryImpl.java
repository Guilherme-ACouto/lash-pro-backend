package com.lashmanager.appointments.infrastructure.persistence.repository;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.appointments.infrastructure.persistence.entity.AppointmentEntity;
import com.lashmanager.appointments.infrastructure.persistence.mapper.AppointmentMapper;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.services.infrastructure.persistence.repository.ServiceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final AppointmentJpaRepository jpaRepository;
    private final AppointmentMapper mapper;
    private final ClientJpaRepository clientJpaRepository;
    private final ServiceJpaRepository serviceJpaRepository;

    @Override
    public Appointment save(Appointment appointment) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(appointment)));
    }

    @Override
    public Optional<Appointment> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findActiveByDate(LocalDate date) {
        return jpaRepository.findActiveByDate(date).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> findByDateWithDetails(LocalDate date) {
        return jpaRepository.findByDate(date).stream()
                .map(this::toResult)
                .toList();
    }

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> findByDateRangeWithDetails(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findByDateRange(startDate, endDate).stream()
                .map(this::toResult)
                .toList();
    }

    @Override
    public List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate from) {
        return jpaRepository.findFutureActiveByClientId(clientId, from).stream()
                .map(a -> {
                    String serviceName = serviceJpaRepository.findById(a.getServiceId())
                            .map(s -> s.getName()).orElse("—");
                    return new AppointmentSummary(
                            a.getId().toString(),
                            a.getScheduledDate(),
                            a.getScheduledTime(),
                            serviceName
                    );
                })
                .toList();
    }

    @Override
    public void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate from) {
        jpaRepository.deleteFutureByClientId(clientId, from);
    }

    @Override
    public void unlinkClientFromPastAppointments(UUID clientId, LocalDate from) {
        jpaRepository.unlinkClientFromPastAppointments(clientId, from);
    }

    @Override
    public boolean hasActiveAppointmentsByServiceId(UUID serviceId, LocalDate from) {
        return jpaRepository.countActiveByServiceId(serviceId, from) > 0;
    }

    private CreateAppointmentUseCase.AppointmentResult toResult(AppointmentEntity a) {
        String clientName = a.getClientId() != null
                ? clientJpaRepository.findById(a.getClientId()).map(c -> c.getName()).orElse("—")
                : "—";
        String serviceName = serviceJpaRepository.findById(a.getServiceId())
                .map(s -> s.getName()).orElse("—");
        BigDecimal servicePrice = serviceJpaRepository.findById(a.getServiceId())
                .map(s -> s.getPrice()).orElse(BigDecimal.ZERO);

        return new CreateAppointmentUseCase.AppointmentResult(
                a.getId(),
                a.getClientId(),
                clientName,
                a.getServiceId(),
                serviceName,
                servicePrice,
                a.getScheduledDate().toString(),
                a.getScheduledTime().toString(),
                a.getDurationMinutes(),
                a.getStatus(),
                a.getNotes(),
                a.getFinancialEntryId() != null ? a.getFinancialEntryId().toString() : null,
                a.getCreatedAt().toString()
        );
    }
}
