package com.bravapro.fichas.application.usecase;

import com.bravapro.clients.domain.model.Client;
import com.bravapro.clients.domain.port.in.ClientQueryService;
import com.bravapro.fichas.application.command.CreateMappingCommand;
import com.bravapro.fichas.application.command.UpdateMappingCommand;
import com.bravapro.fichas.domain.model.Mapping;
import com.bravapro.fichas.domain.port.in.MappingUseCase;
import com.bravapro.fichas.domain.port.out.MappingRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MappingUseCaseImpl implements MappingUseCase {

    private final MappingRepository mappingRepository;
    private final ClientQueryService clientQueryService;

    @Override
    public Mapping create(CreateMappingCommand command) {
        Client client = clientQueryService.getById(command.getClientId());

        LocalDateTime now = LocalDateTime.now();
        Mapping mapping = Mapping.builder()
                .id(UUID.randomUUID())
                .clientId(command.getClientId())
                .clientName(client.getName())
                .mappingDate(command.getMappingDate())
                .mappingType(command.getMappingType())
                .curvature(command.getCurvature())
                .humidity(command.getHumidity())
                .temperature(command.getTemperature())
                .thickness(command.getThickness())
                .threadBrand(command.getThreadBrand())
                .threadFormat(command.getThreadFormat())
                .adhesive(command.getAdhesive())
                .lengthsUsed(command.getLengthsUsed())
                .observations(command.getObservations())
                .canvasData(command.getCanvasData())
                .photoBefore(command.getPhotoBefore())
                .photoAfter(command.getPhotoAfter())
                .createdAt(now)
                .updatedAt(now)
                .build();

        return mappingRepository.save(mapping);
    }

    @Override
    public Mapping update(Mapping mapping, UpdateMappingCommand command) {
        mapping.update(command);
        return mappingRepository.save(mapping);
    }

    @Override
    public void delete(Mapping mapping) {
        mappingRepository.delete(mapping.getId());
    }
}
