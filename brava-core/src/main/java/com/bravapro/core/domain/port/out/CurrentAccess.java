package com.bravapro.core.domain.port.out;

import com.bravapro.core.domain.permission.Permission;

import java.util.Set;
import java.util.UUID;

/**
 * Quem está fazendo a requisição e o que pode fazer nela — equivalente ao
 * {@code RequestAccessSnapshot} da Pontta. As permissões finas são resolvidas por requisição (nunca
 * vão no JWT), então mudanças feitas pela administradora valem na próxima chamada do usuário.
 */
public interface CurrentAccess {

    boolean isAuthenticated();

    UUID userId();

    String email();

    String userName();

    /** Assinatura em que a requisição está operando (a do token — pode ser outra no modo suporte). */
    UUID tenantId();

    /** Administradora da assinatura atual, ou equipe da plataforma em modo suporte: acesso total. */
    boolean isAdmin();

    /** E-mail do domínio da plataforma (Brava Pro). */
    boolean isPlatformAdmin();

    /** Equipe da plataforma operando dentro de uma assinatura que não é a dela. */
    boolean isSupportSession();

    boolean has(Permission permission);

    /** Chaves concedidas (vazio pra administradora — ela não depende delas). */
    Set<String> permissionKeys();
}
