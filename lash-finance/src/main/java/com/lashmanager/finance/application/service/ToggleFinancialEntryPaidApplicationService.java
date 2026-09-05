package com.lashmanager.finance.application.service;

import com.lashmanager.finance.application.command.ToggleFinancialEntryPaidCommand;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.finance.domain.port.in.ToggleFinancialEntryPaidUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToggleFinancialEntryPaidApplicationService {

    private final ToggleFinancialEntryPaidUseCase toggleFinancialEntryPaidUseCase;

    public ListFinancialEntriesUseCase.EntryResult when(ToggleFinancialEntryPaidCommand command) {
        return toggleFinancialEntryPaidUseCase.execute(command.getId());
    }
}
