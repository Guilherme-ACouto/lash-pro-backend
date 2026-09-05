package com.lashmanager.finance.application.service;

import com.lashmanager.finance.application.command.CreateFinancialEntryCommand;
import com.lashmanager.finance.domain.port.in.CreateFinancialEntryUseCase;
import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFinancialEntryApplicationService {

  private final CreateFinancialEntryUseCase createFinancialEntryUseCase;

  public ListFinancialEntriesUseCase.EntryResult when(CreateFinancialEntryCommand command) {
    return createFinancialEntryUseCase.execute(command.toDomainCommand());
  }
}
