package com.lashmanager.fichas.application.query;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientQueryService;
import com.lashmanager.fichas.domain.exception.AnamneseLinkExpiredException;
import com.lashmanager.fichas.domain.exception.AnamneseLinkInvalidException;
import com.lashmanager.fichas.domain.model.Anamnese;
import com.lashmanager.fichas.domain.model.AnamnesePublicView;
import com.lashmanager.fichas.domain.port.in.PublicAnamneseQueryService;
import com.lashmanager.fichas.domain.port.out.AnamneseQueryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicAnamneseQueryServiceImpl implements PublicAnamneseQueryService {

    private final AnamneseQueryRepository anamneseQueryRepository;
    private final ClientQueryService clientQueryService;

    @Override
    public AnamnesePublicView getByToken(String token) {
        Anamnese anamnese =
                anamneseQueryRepository.findByLinkToken(token).orElseThrow(AnamneseLinkInvalidException::new);
        if (anamnese.isLinkExpired()) {
            throw new AnamneseLinkExpiredException();
        }
        Client client = clientQueryService.getById(anamnese.getClientId());
        return new AnamnesePublicView(client.getName(), client.getPhone(), anamnese);
    }
}
