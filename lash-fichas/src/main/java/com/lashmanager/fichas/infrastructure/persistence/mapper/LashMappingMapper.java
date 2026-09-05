package com.lashmanager.fichas.infrastructure.persistence.mapper;

import com.lashmanager.fichas.domain.model.LashMapping;
import com.lashmanager.fichas.infrastructure.persistence.entity.FichaEntity;
import com.lashmanager.fichas.infrastructure.persistence.entity.LashMappingEntity;
import com.lashmanager.fichas.infrastructure.persistence.repository.FichaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LashMappingMapper {

  private final FichaJpaRepository fichaJpaRepository;

  public LashMapping toDomain(LashMappingEntity e) {
    return LashMapping.builder()
        .id(e.getId())
        .fichaId(e.getFicha() != null ? e.getFicha().getId() : null)
        .appointmentId(e.getAppointmentId())
        .date(e.getDate())
        .technique(e.getTechnique())
        .curvature(e.getCurvature())
        .thickness(e.getThickness())
        .length(e.getLength())
        .rightEyeNotes(e.getRightEyeNotes())
        .leftEyeNotes(e.getLeftEyeNotes())
        .notes(e.getNotes())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .build();
  }

  public LashMappingEntity toEntity(LashMapping m) {
    FichaEntity fichaRef = fichaJpaRepository.getReferenceById(m.getFichaId());
    return LashMappingEntity.builder()
        .id(m.getId())
        .ficha(fichaRef)
        .appointmentId(m.getAppointmentId())
        .date(m.getDate())
        .technique(m.getTechnique())
        .curvature(m.getCurvature())
        .thickness(m.getThickness())
        .length(m.getLength())
        .rightEyeNotes(m.getRightEyeNotes())
        .leftEyeNotes(m.getLeftEyeNotes())
        .notes(m.getNotes())
        .createdAt(m.getCreatedAt())
        .updatedAt(m.getUpdatedAt())
        .build();
  }
}
