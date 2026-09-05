package com.lashmanager.finance.application.service;

import com.lashmanager.finance.application.command.DeleteFinancialEntryCommand;
import com.lashmanager.finance.domain.port.in.DeleteFinancialEntryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFinancialEntryApplicationService {

  private final DeleteFinancialEntryUseCase deleteFinancialEntryUseCase;

  public void when(DeleteFinancialEntryCommand command) {
    deleteFinancialEntryUseCase.execute(command.getId());
  }
}
