package com.lashmanager.app.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ListMappingsByClientUseCase {
    Page<CreateLashMappingUseCase.MappingResult> execute(UUID clientId, Pageable pageable);
}
