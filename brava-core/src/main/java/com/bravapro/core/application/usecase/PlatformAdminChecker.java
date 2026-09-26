package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.exception.PlatformAdminRequiredException;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Admin de plataforma = usuário com e-mail do domínio da própria empresa (padrão Pontta). Ele
 * continua pertencendo a um tenant como qualquer usuário; o domínio só libera a administração de
 * todos os tenants. Criar conta com esse domínio exige ativação por e-mail, então só entra quem tem
 * acesso à caixa de entrada do domínio.
 */
@Component
public class PlatformAdminChecker {

    private final String adminEmailSuffix;

    public PlatformAdminChecker(@Value("${app.platform.admin-email-domain}") String adminEmailDomain) {
        this.adminEmailSuffix = "@" + adminEmailDomain.toLowerCase(Locale.ROOT);
    }

    public void check() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null || !email.toLowerCase(Locale.ROOT).endsWith(adminEmailSuffix)) {
            throw new PlatformAdminRequiredException();
        }
    }
}
