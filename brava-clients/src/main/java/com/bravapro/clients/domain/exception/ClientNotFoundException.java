package com.bravapro.clients.domain.exception;

import com.bravapro.core.domain.exception.BusinessException;
import java.util.UUID;

public class ClientNotFoundException extends BusinessException {
    public ClientNotFoundException(UUID id) {
        super("Cliente não encontrado: " + id);
    }
}
