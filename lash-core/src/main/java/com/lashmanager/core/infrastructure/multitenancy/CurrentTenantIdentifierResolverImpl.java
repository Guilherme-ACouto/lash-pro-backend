package com.lashmanager.core.infrastructure.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl
    implements CurrentTenantIdentifierResolver<String> {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenant = TenantContext.getCurrentTenant();
    return tenant != null ? tenant : TenantContext.DEFAULT_TENANT;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return false;
  }
}
