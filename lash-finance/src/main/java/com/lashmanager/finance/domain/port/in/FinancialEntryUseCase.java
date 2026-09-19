package com.lashmanager.finance.domain.port.in;

import com.lashmanager.finance.application.command.CreateFinancialEntryCommand;
import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;
import com.lashmanager.finance.domain.model.FinancialEntry;

public interface FinancialEntryUseCase {

    FinancialEntry create(CreateFinancialEntryCommand command);

    void update(FinancialEntry entry, UpdateFinancialEntryCommand command);

    void delete(FinancialEntry entry);

    void togglePaid(FinancialEntry entry);
}
