package com.lashmanager.fichas.application.usecase;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientQueryService;
import com.lashmanager.fichas.application.command.SaveAnamneseCommand;
import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.GeneratedAnamneseLink;
import com.lashmanager.fichas.domain.port.in.AnamneseUseCase;
import com.lashmanager.fichas.domain.port.out.AnamneseRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnamneseUseCaseImpl implements AnamneseUseCase {

    private final AnamneseRepository anamneseRepository;
    private final ClientQueryService clientQueryService;

    @Value("${app.anamnese.link-expiration-hours:72}")
    private int linkExpirationHours;

    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private String frontendUrl;

    @Override
    public Anamnese save(SaveAnamneseCommand command) {
        Anamnese anamnese = anamneseRepository
                .findByClientId(command.getClientId())
                .orElseGet(() -> newAnamneseFor(command.getClientId()));
        anamnese.update(command);
        return anamneseRepository.save(anamnese);
    }

    @Override
    public Anamnese update(Anamnese anamnese, SaveAnamneseCommand command) {
        anamnese.update(command);
        return anamneseRepository.save(anamnese);
    }

    @Override
    public GeneratedAnamneseLink generateLink(UUID clientId) {
        Anamnese anamnese = anamneseRepository.findByClientId(clientId).orElseGet(() -> newAnamneseFor(clientId));
        anamnese.generateLink(linkExpirationHours);
        Anamnese saved = anamneseRepository.save(anamnese);
        return new GeneratedAnamneseLink(frontendUrl + "/ficha/" + saved.getLinkToken());
    }

    private Anamnese newAnamneseFor(UUID clientId) {
        Client client = clientQueryService.getById(clientId);
        LocalDateTime now = LocalDateTime.now();
        return Anamnese.builder()
                .id(UUID.randomUUID())
                .clientId(clientId)
                .clientName(client.getName())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
