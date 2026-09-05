package com.lashmanager.finance.domain.port.in;

import java.util.UUID;

public interface ToggleFinancialEntryPaidUseCase {
    ListFinancialEntriesUseCase.EntryResult execute(UUID id);
}
