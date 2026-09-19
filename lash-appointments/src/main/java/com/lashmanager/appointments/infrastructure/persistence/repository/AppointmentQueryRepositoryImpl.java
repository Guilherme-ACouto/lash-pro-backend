package com.lashmanager.appointments.infrastructure.persistence.repository;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentDetails;
import com.lashmanager.appointments.domain.port.out.AppointmentQueryRepository;
import com.lashmanager.appointments.infrastructure.persistence.entity.AppointmentEntity;
import com.lashmanager.appointments.infrastructure.persistence.mapper.AppointmentMapper;
import com.lashmanager.clients.infrastructure.persistence.entity.ClientEntity;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.services.infrastructure.persistence.entity.ServiceEntity;
import com.lashmanager.services.infrastructure.persistence.repository.ServiceJpaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppointmentQueryRepositoryImpl implements AppointmentQueryRepository {

    private final AppointmentJpaRepository jpaRepository;
    private final AppointmentMapper mapper;
    private final ClientJpaRepository clientJpaRepository;
    private final ServiceJpaRepository serviceJpaRepository;

    @Override
    public Optional<Appointment> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<AppointmentDetails> findByDateWithDetails(LocalDate date) {
        return jpaRepository.findByDate(date).stream().map(this::toResult).toList();
    }

    @Override
    public List<AppointmentDetails> findByDateRangeWithDetails(
            LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findByDateRange(startDate, endDate).stream()
                .map(this::toResult)
                .toList();
    }

    private AppointmentDetails toResult(AppointmentEntity a) {
        String clientName = a.getClientId() != null
                ? clientJpaRepository
                        .findById(a.getClientId())
                        .map(ClientEntity::getName)
                        .orElse("—")
                : "—";
        String serviceName = serviceJpaRepository
                .findById(a.getServiceId())
                .map(ServiceEntity::getName)
                .orElse("—");
        BigDecimal servicePrice = serviceJpaRepository
                .findById(a.getServiceId())
                .map(ServiceEntity::getPrice)
                .orElse(BigDecimal.ZERO);

        return new AppointmentDetails(
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
                a.getCreatedAt().toString());
    }
}
