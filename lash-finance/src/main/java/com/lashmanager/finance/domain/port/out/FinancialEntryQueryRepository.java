package com.lashmanager.finance.domain.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Porta de leitura — separada de FinancialEntryRepository (escrita) conforme
 * RBK-27. Encontrada faltando na varredura pós-Fase F: T33 só formalizou
 * FinancialSummaryRepositoryImpl (agregações), deixando passar essa outra
 * leitura (listagem/filtro comum) que seguia o mesmo padrão dos demais módulos.
 */
public interface FinancialEntryQueryRepository {

    Page<FinancialEntryRepository.FinancialEntryWithCounterpart> listWithFilters(
            LocalDate from, LocalDate to,
            String category, String expenseType, String type,
            Pageable pageable
    );

    List<String> findDistinctCategories();
}
