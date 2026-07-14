package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.ListFichasUseCase;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ListFichasUseCaseImpl implements ListFichasUseCase {

    private final FichaRepository fichaRepository;

    @Override
    public Page<CreateFichaUseCase.FichaResult> execute(ListFichasQuery query, Pageable pageable) {
        String search = query.search() != null ? query.search() : "";
        return fichaRepository.listWithFilters(search, pageable)
                .map(FichaUseCaseMapper::toFichaResult);
    }
}
