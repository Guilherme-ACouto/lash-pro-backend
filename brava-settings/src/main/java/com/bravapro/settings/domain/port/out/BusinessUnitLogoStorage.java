package com.bravapro.settings.domain.port.out;

import java.util.Optional;
import java.util.UUID;

/**
 * Onde o arquivo do logo mora (como o {@code BusinessUnitLogoStorage} da Pontta). O banco guarda
 * só a chave e a URL. Hoje a implementação grava em disco; trocar por S3 é trocar o adapter.
 */
public interface BusinessUnitLogoStorage {

    record StoredLogo(String key, String url) {}

    record LogoFile(byte[] content, String contentType) {}

    StoredLogo store(UUID tenantId, byte[] content, String contentType);

    Optional<LogoFile> load(UUID tenantId, String fileName);

    void delete(String key);
}
