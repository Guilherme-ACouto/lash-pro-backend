package com.lashmanager.fichas.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ListLashMappingsUseCase {
    Page<CreateLashMappingUseCase.LashMappingResult> execute(UUID fichaId, Pageable pageable);
}
