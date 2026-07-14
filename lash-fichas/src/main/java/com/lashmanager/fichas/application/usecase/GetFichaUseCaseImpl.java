package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.exception.FichaNotFoundException;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.GetFichaUseCase;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetFichaUseCaseImpl implements GetFichaUseCase {

    private final FichaRepository fichaRepository;

    @Override
    public CreateFichaUseCase.FichaResult execute(UUID id) {
        return fichaRepository.findById(id)
                .map(FichaUseCaseMapper::toFichaResult)
                .orElseThrow(() -> new FichaNotFoundException(id));
    }

    @Override
    public CreateFichaUseCase.FichaResult executeByClient(UUID clientId) {
        return fichaRepository.findByClientId(clientId)
                .map(FichaUseCaseMapper::toFichaResult)
                .orElseThrow(() -> new FichaNotFoundException(clientId));
    }
}
