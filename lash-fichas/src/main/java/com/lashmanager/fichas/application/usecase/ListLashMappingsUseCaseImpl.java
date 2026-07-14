package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.port.in.CreateLashMappingUseCase;
import com.lashmanager.fichas.domain.port.in.ListLashMappingsUseCase;
import com.lashmanager.fichas.domain.port.out.LashMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListLashMappingsUseCaseImpl implements ListLashMappingsUseCase {

    private final LashMappingRepository mappingRepository;

    @Override
    public Page<CreateLashMappingUseCase.LashMappingResult> execute(UUID fichaId, Pageable pageable) {
        return mappingRepository.findByFichaId(fichaId, pageable)
                .map(FichaUseCaseMapper::toMappingResult);
    }
}
