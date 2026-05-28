package com.lashmanager.app.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ListAnamneseSummaryUseCase {
    record AnamneseSummaryResult(UUID clientId, String clientName, boolean hasAnamnese, String updatedAt) {}

    Page<AnamneseSummaryResult> execute(String search, Pageable pageable);
}
