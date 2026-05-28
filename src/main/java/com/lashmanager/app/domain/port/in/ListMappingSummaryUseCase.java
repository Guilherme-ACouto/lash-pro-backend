package com.lashmanager.app.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListMappingSummaryUseCase {
    record MappingSummaryResult(String clientId, String clientName, long mappingCount, String lastMappingDate) {}

    Page<MappingSummaryResult> execute(String search, Pageable pageable);
}
