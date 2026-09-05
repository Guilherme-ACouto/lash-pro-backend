package com.lashmanager.finance.domain.port.out;

import com.lashmanager.finance.domain.model.FinancialEntry;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem/filtro, categorias distintas) foi separada para
 * FinancialEntryQueryRepository (RBK-27).
 */
public interface FinancialEntryRepository {

  record FinancialEntryWithCounterpart(FinancialEntry entry, String counterpart) {}

  FinancialEntry save(FinancialEntry entry);

  Optional<FinancialEntry> findById(UUID id);

  void delete(UUID id);

  boolean existsByIdAndAppointmentIdIsNull(UUID id);
}
