package com.bravapro.fichas.application.query;

import com.bravapro.clients.domain.model.Client;
import com.bravapro.clients.domain.port.in.ClientQueryService;
import com.bravapro.fichas.domain.exception.AnamneseLinkExpiredException;
import com.bravapro.fichas.domain.exception.AnamneseLinkInvalidException;
import com.bravapro.fichas.domain.model.Anamnese;
import com.bravapro.fichas.domain.model.AnamnesePublicView;
import com.bravapro.fichas.domain.port.in.PublicAnamneseQueryService;
import com.bravapro.fichas.domain.port.out.AnamneseQueryRepository;

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
