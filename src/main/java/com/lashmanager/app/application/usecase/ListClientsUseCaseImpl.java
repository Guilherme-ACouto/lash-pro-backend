package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.CreateClientUseCase;
import com.lashmanager.app.domain.port.in.ListClientsUseCase;
import com.lashmanager.app.domain.port.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListClientsUseCaseImpl implements ListClientsUseCase {

    private final ClientRepository clientRepository;

    @Override
    public Page<CreateClientUseCase.ClientResult> execute(String search, Boolean active, Pageable pageable) {
        String normalizedSearch = (search != null) ? search.trim() : "";
        return clientRepository.findAll(normalizedSearch, active, pageable)
                .map(ClientUseCaseMapper::toResult);
    }
}
