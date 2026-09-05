package com.lashmanager.core.domain.exception;

public class TenantNotFoundException extends DomainException {
  public TenantNotFoundException() {
    super("Tenant não encontrado");
  }
}
