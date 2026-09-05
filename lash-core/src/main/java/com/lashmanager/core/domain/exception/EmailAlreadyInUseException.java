package com.lashmanager.core.domain.exception;

public class EmailAlreadyInUseException extends BusinessException {
  public EmailAlreadyInUseException(String email) {
    super("E-mail já em uso: " + email);
  }
}
