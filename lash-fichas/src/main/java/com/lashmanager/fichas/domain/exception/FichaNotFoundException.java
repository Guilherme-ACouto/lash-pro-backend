package com.lashmanager.fichas.domain.exception;

import com.lashmanager.core.domain.exception.DomainException;
import java.util.UUID;

public class FichaNotFoundException extends DomainException {
  public FichaNotFoundException(UUID id) {
    super("Ficha não encontrada: " + id);
  }
}
