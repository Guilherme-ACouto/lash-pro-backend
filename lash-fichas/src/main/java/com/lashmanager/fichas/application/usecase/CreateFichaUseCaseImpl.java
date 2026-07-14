package com.lashmanager.fichas.application.usecase;

import com.lashmanager.clients.domain.port.in.GetClientUseCase;
import com.lashmanager.fichas.domain.exception.ClientAlreadyHasFichaException;
import com.lashmanager.fichas.domain.model.Ficha;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateFichaUseCaseImpl implements CreateFichaUseCase {

    private final FichaRepository fichaRepository;
    private final GetClientUseCase getClientUseCase;

    @Override
    public FichaResult execute(CreateFichaCommand command) {
        if (fichaRepository.existsByClientId(command.clientId())) {
            throw new ClientAlreadyHasFichaException(command.clientId());
        }

        var clientResult = getClientUseCase.execute(command.clientId());

        Ficha ficha = Ficha.builder()
                .id(UUID.randomUUID())
                .clientId(command.clientId())
                .clientName(clientResult.name())
                .date(command.date())
                .skinType(command.skinType())
                .eyeShape(command.eyeShape())
                .hasAllergies(command.hasAllergies())
                .allergiesDescription(command.allergiesDescription())
                .hasMedications(command.hasMedications())
                .medicationsDescription(command.medicationsDescription())
                .hasSensitivities(command.hasSensitivities())
                .sensitivitiesDescription(command.sensitivitiesDescription())
                .observations(command.observations())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return FichaUseCaseMapper.toFichaResult(fichaRepository.save(ficha));
    }
}
