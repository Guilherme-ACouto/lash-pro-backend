package com.lashmanager.clients.application.query;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.in.ClientQueryService;
import com.lashmanager.clients.domain.port.out.ClientQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientQueryServiceImpl implements ClientQueryService {

    private final ClientQueryRepository clientQueryRepository;

    @Override
    public Client getById(UUID id) {
        return clientQueryRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public Page<Client> list(String search, Boolean active, Pageable pageable) {
        String normalizedSearch = search == null ? "" : search.trim();
        return clientQueryRepository.findAll(normalizedSearch, active, pageable);
    }
}
