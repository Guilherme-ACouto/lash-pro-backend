package com.bravapro.finance.domain.port.in;

import com.bravapro.finance.application.command.CreateFinancialEntryCommand;
import com.bravapro.finance.application.command.UpdateFinancialEntryCommand;
import com.bravapro.finance.domain.model.FinancialEntry;

public interface FinancialEntryUseCase {

    FinancialEntry create(CreateFinancialEntryCommand command);

    void update(FinancialEntry entry, UpdateFinancialEntryCommand command);

    void delete(FinancialEntry entry);

    void togglePaid(FinancialEntry entry);
}
