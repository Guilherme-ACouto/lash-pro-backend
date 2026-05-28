package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.LashMapping;
import com.lashmanager.app.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CreateLashMappingUseCaseImpl implements CreateLashMappingUseCase {
    private final LashMappingRepository repository;

    @Override public MappingResult execute(CreateMappingCommand command) {
        LashMapping mapping = LashMapping.builder()
            .clientId(command.clientId()).mappingDate(command.mappingDate())
            .mappingType(command.mappingType()).curvature(command.curvature())
            .humidity(command.humidity()).temperature(command.temperature())
            .thickness(command.thickness()).threadBrand(command.threadBrand())
            .threadFormat(command.threadFormat()).adhesive(command.adhesive())
            .lengthsUsed(command.lengthsUsed()).observations(command.observations())
            .canvasData(command.canvasData()).photoBefore(command.photoBefore())
            .photoAfter(command.photoAfter()).build();
        return toResult(repository.save(mapping));
    }

    public static MappingResult toResult(LashMapping m) {
        return new MappingResult(
            m.getId() != null ? m.getId().toString() : null,
            m.getClientId() != null ? m.getClientId().toString() : null,
            m.getClientName(), m.getMappingDate() != null ? m.getMappingDate().toString() : null,
            m.getMappingType(), m.getCurvature(), m.getHumidity(), m.getTemperature(),
            m.getThickness(), m.getThreadBrand(), m.getThreadFormat(), m.getAdhesive(),
            m.getLengthsUsed(), m.getObservations(), m.getCanvasData(),
            m.getPhotoBefore(), m.getPhotoAfter(),
            m.getCreatedAt() != null ? m.getCreatedAt().toString() : null,
            m.getUpdatedAt() != null ? m.getUpdatedAt().toString() : null
        );
    }
}
