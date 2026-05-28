package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.infrastructure.persistence.entity.AppointmentEntity;
import com.lashmanager.app.infrastructure.persistence.entity.ClientEntity;
import com.lashmanager.app.infrastructure.persistence.entity.ServiceEntity;
import com.lashmanager.app.infrastructure.persistence.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentMapper mapper;
    private final ClientJpaRepository clientJpaRepository;
    private final ServiceJpaRepository serviceJpaRepository;

    @Override
    public Appointment save(Appointment appointment) {
        ClientEntity clientEntity = clientJpaRepository.findById(appointment.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(appointment.getClientId()));
        ServiceEntity serviceEntity = serviceJpaRepository.findById(appointment.getServiceId())
                .orElseThrow(() -> new ServiceNotFoundException(appointment.getServiceId()));

        AppointmentEntity entity = mapper.toEntity(appointment, clientEntity, serviceEntity);
        AppointmentEntity saved = appointmentJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Appointment> findById(UUID id) {
        return appointmentJpaRepository.findByIdWithDetails(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findActiveByDate(LocalDate date) {
        return appointmentJpaRepository.findActiveByDate(date).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> findByDateWithDetails(LocalDate date) {
        return appointmentJpaRepository.findByDateWithDetails(date).stream()
                .map(mapper::toResult)
                .toList();
    }

    @Override
    public List<CreateAppointmentUseCase.AppointmentResult> findByDateRangeWithDetails(LocalDate startDate, LocalDate endDate) {
        return appointmentJpaRepository.findByDateRangeWithDetails(startDate, endDate).stream()
                .map(mapper::toResult)
                .toList();
    }

    @Override
    public long countFutureActiveByClientId(UUID clientId, LocalDate today) {
        return appointmentJpaRepository.countFutureActiveByClientId(clientId, today);
    }

    @Override
    public long countFutureActiveByServiceId(UUID serviceId, LocalDate today) {
        return appointmentJpaRepository.countFutureActiveByServiceId(serviceId, today);
    }

    @Override
    public long countAllByClientId(UUID clientId) {
        return appointmentJpaRepository.countAllByClientId(clientId);
    }

    @Override
    public long countAllByServiceId(UUID serviceId) {
        return appointmentJpaRepository.countAllByServiceId(serviceId);
    }

    @Override
    public List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate today) {
        return appointmentJpaRepository.findFutureActiveByClientId(clientId, today).stream()
                .map(a -> new AppointmentSummary(
                        a.getId().toString(),
                        a.getScheduledDate(),
                        a.getScheduledTime(),
                        a.getService().getName()
                ))
                .toList();
    }

    @Override
    public List<AppointmentSummary> findFutureActiveByServiceId(UUID serviceId, LocalDate today) {
        return appointmentJpaRepository.findFutureActiveByServiceId(serviceId, today).stream()
                .map(a -> new AppointmentSummary(
                        a.getId().toString(),
                        a.getScheduledDate(),
                        a.getScheduledTime(),
                        a.getClient() != null ? a.getClient().getName() : "—"
                ))
                .toList();
    }

    @Override
    public void unlinkClientFromPastAppointments(UUID clientId, LocalDate today) {
        appointmentJpaRepository.unlinkClientFromPastAppointments(clientId, today);
    }

    @Override
    public void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate today) {
        appointmentJpaRepository.deleteFutureByClientId(clientId, today);
    }
}
