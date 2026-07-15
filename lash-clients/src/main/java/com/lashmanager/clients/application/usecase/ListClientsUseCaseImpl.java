package com.lashmanager.clients.application.usecase;

import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import com.lashmanager.clients.domain.port.in.ListClientsUseCase;
import com.lashmanager.clients.domain.port.out.ClientQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ListClientsUseCaseImpl implements ListClientsUseCase {

    private final ClientQueryRepository clientQueryRepository;

    @Override
    public Page<CreateClientUseCase.ClientResult> execute(String search, Boolean active, Pageable pageable) {
        String normalizedSearch = search == null ? "" : search.trim();
        return clientQueryRepository.findAll(normalizedSearch, active, pageable)
                .map(ClientUseCaseMapper::toResult);
    }
}
