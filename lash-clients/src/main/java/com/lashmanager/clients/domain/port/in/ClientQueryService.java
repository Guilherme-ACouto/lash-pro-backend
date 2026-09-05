package com.lashmanager.clients.domain.port.in;

import com.lashmanager.clients.domain.model.Client;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientQueryService {

    Client getById(UUID id);

    Page<Client> list(String search, Boolean active, Pageable pageable);
}
