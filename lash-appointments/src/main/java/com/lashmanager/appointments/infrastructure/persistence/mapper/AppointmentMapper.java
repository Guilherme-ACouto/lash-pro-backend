package com.lashmanager.appointments.infrastructure.persistence.mapper;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentStatus;
import com.lashmanager.appointments.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

  public Appointment toDomain(AppointmentEntity entity) {
    return Appointment.builder()
        .id(entity.getId())
        .clientId(entity.getClientId())
        .serviceId(entity.getServiceId())
        .scheduledDate(entity.getScheduledDate())
        .scheduledTime(entity.getScheduledTime())
        .durationMinutes(entity.getDurationMinutes())
        .status(AppointmentStatus.valueOf(entity.getStatus()))
        .notes(entity.getNotes())
        .financialEntryId(entity.getFinancialEntryId())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public AppointmentEntity toEntity(Appointment domain) {
    return AppointmentEntity.builder()
        .id(domain.getId())
        .clientId(domain.getClientId())
        .serviceId(domain.getServiceId())
        .scheduledDate(domain.getScheduledDate())
        .scheduledTime(domain.getScheduledTime())
        .durationMinutes(domain.getDurationMinutes())
        .status(domain.getStatus().name())
        .notes(domain.getNotes())
        .financialEntryId(domain.getFinancialEntryId())
        .createdAt(domain.getCreatedAt())
        .updatedAt(domain.getUpdatedAt())
        .build();
  }
}
