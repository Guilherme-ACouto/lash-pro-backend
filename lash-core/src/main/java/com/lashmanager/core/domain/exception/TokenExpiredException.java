package com.lashmanager.core.domain.exception;

public class TokenExpiredException extends DomainException {
  public TokenExpiredException() {
    super("Token expirado ou inválido");
  }
}
