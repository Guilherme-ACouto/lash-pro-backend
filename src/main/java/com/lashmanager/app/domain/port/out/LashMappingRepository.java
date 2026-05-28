package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.LashMapping;
import com.lashmanager.app.domain.port.in.ListMappingSummaryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface LashMappingRepository {
    LashMapping save(LashMapping mapping);
    Optional<LashMapping> findById(UUID id);
    Page<LashMapping> findByClientId(UUID clientId, Pageable pageable);
    Page<ListMappingSummaryUseCase.MappingSummaryResult> findAllSummary(String search, Pageable pageable);
    void delete(UUID id);
}
