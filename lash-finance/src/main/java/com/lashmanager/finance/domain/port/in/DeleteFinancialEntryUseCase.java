package com.lashmanager.finance.domain.port.in;

import java.util.UUID;

public interface DeleteFinancialEntryUseCase {
  void execute(UUID id);
}
