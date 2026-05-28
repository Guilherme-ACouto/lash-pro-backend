package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.LashMappingNotFoundException;
import com.lashmanager.app.domain.port.in.DeleteLashMappingUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteLashMappingUseCaseImpl implements DeleteLashMappingUseCase {

    private final LashMappingRepository repository;

    @Override
    public void execute(UUID id) {
        if (repository.findById(id).isEmpty()) throw new LashMappingNotFoundException(id);
        repository.delete(id);
    }
}
