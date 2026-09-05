package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.model.Ficha;
import com.lashmanager.fichas.domain.model.LashMapping;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.CreateLashMappingUseCase;

public final class FichaUseCaseMapper {

  private FichaUseCaseMapper() {}

  public static CreateFichaUseCase.FichaResult toFichaResult(Ficha f) {
    return new CreateFichaUseCase.FichaResult(
        f.getId(),
        f.getClientId(),
        f.getClientName(),
        f.getDate() != null ? f.getDate().toString() : null,
        f.getSkinType(),
        f.getEyeShape(),
        f.isHasAllergies(),
        f.getAllergiesDescription(),
        f.isHasMedications(),
        f.getMedicationsDescription(),
        f.isHasSensitivities(),
        f.getSensitivitiesDescription(),
        f.getObservations(),
        f.isActive(),
        f.getCreatedAt() != null ? f.getCreatedAt().toString() : null);
  }

  public static CreateLashMappingUseCase.LashMappingResult toMappingResult(LashMapping m) {
    return new CreateLashMappingUseCase.LashMappingResult(
        m.getId(),
        m.getFichaId(),
        m.getAppointmentId(),
        m.getDate() != null ? m.getDate().toString() : null,
        m.getTechnique(),
        m.getCurvature(),
        m.getThickness(),
        m.getLength(),
        m.getRightEyeNotes(),
        m.getLeftEyeNotes(),
        m.getNotes(),
        m.getCreatedAt() != null ? m.getCreatedAt().toString() : null);
  }
}
