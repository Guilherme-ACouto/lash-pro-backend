package com.lashmanager.finance.application.service;

import com.lashmanager.finance.application.command.UpdateFinancialEntryCommand;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import com.lashmanager.finance.domain.port.in.UpdateFinancialEntryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateFinancialEntryApplicationService {

    private final UpdateFinancialEntryUseCase updateFinancialEntryUseCase;

    public ListFinancialEntriesUseCase.EntryResult when(UpdateFinancialEntryCommand command) {
        return updateFinancialEntryUseCase.execute(command.getId(), command.toDomainCommand());
    }
}
