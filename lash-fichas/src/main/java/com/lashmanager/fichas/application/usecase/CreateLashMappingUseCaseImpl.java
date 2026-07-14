package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.exception.FichaNotFoundException;
import com.lashmanager.fichas.domain.model.LashMapping;
import com.lashmanager.fichas.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import com.lashmanager.fichas.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateLashMappingUseCaseImpl implements CreateLashMappingUseCase {

    private final LashMappingRepository mappingRepository;
    private final FichaRepository fichaRepository;

    @Override
    public LashMappingResult execute(CreateLashMappingCommand command) {
        if (fichaRepository.findById(command.fichaId()).isEmpty()) {
            throw new FichaNotFoundException(command.fichaId());
        }

        LashMapping mapping = LashMapping.builder()
                .id(UUID.randomUUID())
                .fichaId(command.fichaId())
                .appointmentId(command.appointmentId())
                .date(command.date())
                .technique(command.technique())
                .curvature(command.curvature())
                .thickness(command.thickness())
                .length(command.length())
                .rightEyeNotes(command.rightEyeNotes())
                .leftEyeNotes(command.leftEyeNotes())
                .notes(command.notes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return FichaUseCaseMapper.toMappingResult(mappingRepository.save(mapping));
    }
}
