package com.lashmanager.core.domain.model;

import java.util.UUID;

/**
 * Marca um modelo de domínio como identificável, permitindo que o {@code RestUtils}
 * (camada web) monte respostas de sucesso genéricas sem conhecer o tipo concreto —
 * só precisa do id pro header {@code X-lash-params}.
 */
public interface DomainEntity {
    UUID getId();
}
