package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.Appointment;
import com.lashmanager.app.domain.model.AppointmentStatus;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase.AppointmentResult;
import com.lashmanager.app.infrastructure.persistence.entity.AppointmentEntity;
import com.lashmanager.app.infrastructure.persistence.entity.ClientEntity;
import com.lashmanager.app.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toDomain(AppointmentEntity e) {
        return Appointment.builder()
                .id(e.getId())
                .clientId(e.getClient() != null ? e.getClient().getId() : null)
                .serviceId(e.getService().getId())
                .scheduledDate(e.getScheduledDate())
                .scheduledTime(e.getScheduledTime())
                .durationMinutes(e.getDurationMinutes())
                .status(AppointmentStatus.valueOf(e.getStatus()))
                .notes(e.getNotes())
                .financialEntryId(e.getFinancialEntryId())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public AppointmentEntity toEntity(Appointment a, ClientEntity client, ServiceEntity service) {
        return AppointmentEntity.builder()
                .id(a.getId())
                .client(client)
                .service(service)
                .scheduledDate(a.getScheduledDate())
                .scheduledTime(a.getScheduledTime())
                .durationMinutes(a.getDurationMinutes())
                .status(a.getStatus().name())
                .notes(a.getNotes())
                .financialEntryId(a.getFinancialEntryId())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }

    public AppointmentResult toResult(AppointmentEntity e) {
        return new AppointmentResult(
                e.getId(),
                e.getClient() != null ? e.getClient().getId() : null,
                e.getClient() != null ? e.getClient().getName() : "Cliente não encontrado",
                e.getService().getId(),
                e.getService().getName(),
                e.getService().getPrice(),
                e.getScheduledDate().toString(),
                e.getScheduledTime().toString(),
                e.getDurationMinutes(),
                e.getStatus(),
                e.getNotes(),
                e.getFinancialEntryId() != null ? e.getFinancialEntryId().toString() : null,
                e.getCreatedAt().toString()
        );
    }
}
