package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.Anamnese;
import com.lashmanager.app.domain.port.in.ListAnamneseSummaryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface AnamneseRepository {
    Anamnese save(Anamnese anamnese);
    Optional<Anamnese> findByClientId(UUID clientId);
    Page<ListAnamneseSummaryUseCase.AnamneseSummaryResult> findAllSummary(String search, Pageable pageable);
    boolean existsByClientId(UUID clientId);
}
