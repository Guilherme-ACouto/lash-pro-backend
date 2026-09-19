package com.lashmanager.fichas.application.service;

import com.lashmanager.fichas.application.command.CreateMappingCommand;
import com.lashmanager.fichas.application.command.DeleteMappingCommand;
import com.lashmanager.fichas.application.command.UpdateMappingCommand;
import com.lashmanager.fichas.domain.exception.MappingNotFoundException;
import com.lashmanager.fichas.domain.model.Mapping;
import com.lashmanager.fichas.domain.port.in.MappingUseCase;
import com.lashmanager.fichas.domain.port.out.MappingRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MappingApplicationService {

    private final MappingUseCase mappingUseCase;
    private final MappingRepository mappingRepository;

    public Mapping when(CreateMappingCommand command) {
        return mappingUseCase.create(command);
    }

    public Mapping when(UpdateMappingCommand command) {
        return mappingUseCase.update(getOne(command.getId()), command);
    }

    public void when(DeleteMappingCommand command) {
        mappingUseCase.delete(getOne(command.getId()));
    }

    private Mapping getOne(UUID id) {
        return mappingRepository.findById(id).orElseThrow(() -> new MappingNotFoundException(id));
    }
}
