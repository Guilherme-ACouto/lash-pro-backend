package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.exception.FichaNotFoundException;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.in.GetFichaUseCase;
import com.lashmanager.fichas.domain.port.out.FichaQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetFichaUseCaseImpl implements GetFichaUseCase {

    private final FichaQueryRepository fichaQueryRepository;

    @Override
    public CreateFichaUseCase.FichaResult execute(UUID id) {
        return fichaQueryRepository.findById(id)
                .map(FichaUseCaseMapper::toFichaResult)
                .orElseThrow(() -> new FichaNotFoundException(id));
    }

    @Override
    public CreateFichaUseCase.FichaResult executeByClient(UUID clientId) {
        return fichaQueryRepository.findByClientId(clientId)
                .map(FichaUseCaseMapper::toFichaResult)
                .orElseThrow(() -> new FichaNotFoundException(clientId));
    }
}
