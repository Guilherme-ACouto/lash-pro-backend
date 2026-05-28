package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.app.domain.port.in.ListMappingsByClientUseCase;
import com.lashmanager.app.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class ListMappingsByClientUseCaseImpl implements ListMappingsByClientUseCase {
    private final LashMappingRepository repository;

    @Override public Page<CreateLashMappingUseCase.MappingResult> execute(UUID clientId, Pageable pageable) {
        return repository.findByClientId(clientId, pageable).map(CreateLashMappingUseCaseImpl::toResult);
    }
}
