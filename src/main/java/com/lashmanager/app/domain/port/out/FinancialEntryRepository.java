package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.FinancialEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface FinancialEntryRepository {

    record FinancialEntryWithCounterpart(FinancialEntry entry, String counterpart) {}

    FinancialEntry save(FinancialEntry entry);

    Optional<FinancialEntry> findById(UUID id);

    Page<FinancialEntryWithCounterpart> listWithFilters(
            LocalDate from,
            LocalDate to,
            String category,
            String expenseType,
            String type,
            Pageable pageable
    );

    void delete(UUID id);

    boolean existsByIdAndAppointmentIdIsNull(UUID id);

    java.util.List<String> findDistinctCategories();
}
