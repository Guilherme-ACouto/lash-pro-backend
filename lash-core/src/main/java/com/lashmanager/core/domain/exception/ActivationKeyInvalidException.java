package com.lashmanager.core.domain.exception;

public class ActivationKeyInvalidException extends DomainException {
  public ActivationKeyInvalidException() {
    super("Link de ativação inválido");
  }
}
