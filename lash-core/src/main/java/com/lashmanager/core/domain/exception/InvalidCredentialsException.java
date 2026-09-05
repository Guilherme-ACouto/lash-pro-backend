package com.lashmanager.core.domain.exception;

public class InvalidCredentialsException extends DomainException {
  public InvalidCredentialsException() {
    super("Credenciais inválidas");
  }
}
