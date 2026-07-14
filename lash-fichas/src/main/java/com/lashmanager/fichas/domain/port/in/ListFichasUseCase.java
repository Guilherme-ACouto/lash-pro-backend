package com.lashmanager.fichas.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListFichasUseCase {

    record ListFichasQuery(String search) {}

    Page<CreateFichaUseCase.FichaResult> execute(ListFichasQuery query, Pageable pageable);
}
