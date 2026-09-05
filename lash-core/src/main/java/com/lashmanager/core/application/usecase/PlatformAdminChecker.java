package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.exception.PlatformAdminRequiredException;
import com.lashmanager.core.domain.exception.UserNotFoundException;
import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Checagem de regra de negócio compartilhada pelos use cases de administração de tenants: hoje o
 * Lash não tem um papel "admin de plataforma" formal (UserRole só tem OWNER/ASSISTANT, escopados a
 * um tenant) — o sinal correto é o próprio usuário não pertencer a tenant nenhum (tenantId nulo),
 * que é exatamente o caso do usuário de desenvolvimento (admin@lashmanager.com, ver decisão
 * registrada em STATE.md). Introduzir um UserRole novo só para isso seria prematuro sem um segundo
 * papel real no sistema.
 */
@Component
@RequiredArgsConstructor
public class PlatformAdminChecker {

  private final UserRepository userRepository;

  public void check() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    if (user.getTenantId() != null) {
      throw new PlatformAdminRequiredException();
    }
  }
}
