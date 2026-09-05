package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.exception.LashMappingNotFoundException;
import com.lashmanager.fichas.domain.model.LashMapping;
import com.lashmanager.fichas.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.fichas.domain.port.in.UpdateLashMappingUseCase;
import com.lashmanager.fichas.domain.port.out.LashMappingRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateLashMappingUseCaseImpl implements UpdateLashMappingUseCase {

    private final LashMappingRepository mappingRepository;

    @Override
    public CreateLashMappingUseCase.LashMappingResult execute(UpdateLashMappingCommand command) {
        LashMapping existing = mappingRepository
                .findById(command.id())
                .orElseThrow(() -> new LashMappingNotFoundException(command.id()));

        LashMapping updated = existing.toBuilder()
                .date(command.date())
                .technique(command.technique())
                .curvature(command.curvature())
                .thickness(command.thickness())
                .length(command.length())
                .rightEyeNotes(command.rightEyeNotes())
                .leftEyeNotes(command.leftEyeNotes())
                .notes(command.notes())
                .updatedAt(LocalDateTime.now())
                .build();

        return FichaUseCaseMapper.toMappingResult(mappingRepository.save(updated));
    }
}
