package com.lashmanager.app.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListClientsUseCase {
    Page<CreateClientUseCase.ClientResult> execute(String search, Boolean active, Pageable pageable);
}
