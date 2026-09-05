package com.lashmanager.clients.application.service;

import com.lashmanager.clients.application.command.DeleteClientCommand;
import com.lashmanager.clients.domain.port.in.DeleteClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteClientApplicationService {

    private final DeleteClientUseCase deleteClientUseCase;

    public void when(DeleteClientCommand command) {
        deleteClientUseCase.execute(command.getId());
    }
}
