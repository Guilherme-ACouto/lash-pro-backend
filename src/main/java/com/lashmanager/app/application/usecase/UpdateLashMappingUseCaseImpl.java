package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.LashMappingNotFoundException;
import com.lashmanager.app.domain.model.LashMapping;
import com.lashmanager.app.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.app.domain.port.in.UpdateLashMappingUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UpdateLashMappingUseCaseImpl implements UpdateLashMappingUseCase {
    private final LashMappingRepository repository;

    @Override public CreateLashMappingUseCase.MappingResult execute(UpdateMappingCommand command) {
        LashMapping existing = repository.findById(command.id())
            .orElseThrow(() -> new LashMappingNotFoundException(command.id()));
        LashMapping updated = LashMapping.builder()
            .id(existing.getId()).clientId(existing.getClientId())
            .mappingDate(command.mappingDate() != null ? command.mappingDate() : existing.getMappingDate())
            .mappingType(command.mappingType()).curvature(command.curvature())
            .humidity(command.humidity()).temperature(command.temperature())
            .thickness(command.thickness()).threadBrand(command.threadBrand())
            .threadFormat(command.threadFormat()).adhesive(command.adhesive())
            .lengthsUsed(command.lengthsUsed()).observations(command.observations())
            .canvasData(command.canvasData()).photoBefore(command.photoBefore())
            .photoAfter(command.photoAfter()).createdAt(existing.getCreatedAt()).build();
        return CreateLashMappingUseCaseImpl.toResult(repository.save(updated));
    }
}
