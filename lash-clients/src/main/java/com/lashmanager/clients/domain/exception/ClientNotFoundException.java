package com.lashmanager.clients.domain.exception;

import com.lashmanager.core.domain.exception.BusinessException;
import java.util.UUID;

public class ClientNotFoundException extends BusinessException {
  public ClientNotFoundException(UUID id) {
    super("Cliente não encontrado: " + id);
  }
}
