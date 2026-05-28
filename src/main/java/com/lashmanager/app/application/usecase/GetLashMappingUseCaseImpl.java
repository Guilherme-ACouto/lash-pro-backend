package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.LashMappingNotFoundException;
import com.lashmanager.app.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.app.domain.port.in.GetLashMappingUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class GetLashMappingUseCaseImpl implements GetLashMappingUseCase {
    private final LashMappingRepository repository;

    @Override public CreateLashMappingUseCase.MappingResult execute(UUID id) {
        return CreateLashMappingUseCaseImpl.toResult(
            repository.findById(id).orElseThrow(() -> new LashMappingNotFoundException(id)));
    }
}
