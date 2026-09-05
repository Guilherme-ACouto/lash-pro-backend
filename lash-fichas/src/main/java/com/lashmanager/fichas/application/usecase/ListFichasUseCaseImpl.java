package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.ListFichasUseCase;
import com.lashmanager.fichas.domain.port.out.FichaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListFichasUseCaseImpl implements ListFichasUseCase {

  private final FichaQueryRepository fichaQueryRepository;

  @Override
  public Page<CreateFichaUseCase.FichaResult> execute(ListFichasQuery query, Pageable pageable) {
    String search = query.search() != null ? query.search() : "";
    return fichaQueryRepository
        .listWithFilters(search, pageable)
        .map(FichaUseCaseMapper::toFichaResult);
  }
}
