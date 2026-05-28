package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface DeleteFinancialEntryUseCase {
    void execute(UUID id);
}
