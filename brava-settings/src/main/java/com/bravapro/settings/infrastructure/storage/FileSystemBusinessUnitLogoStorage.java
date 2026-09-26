package com.bravapro.settings.infrastructure.storage;

import com.bravapro.settings.domain.port.out.BusinessUnitLogoStorage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Logo em disco: {@code <app.storage.path>/logos/<tenantId>/<uuid>.<ext>}. Servido publicamente
 * por {@code /api/public/logos/{tenantId}/{arquivo}} (o nome é um UUID, não dá pra adivinhar), pra
 * poder aparecer em {@code <img>} e na ficha pública de anamnese sem token.
 */
@Component
@Slf4j
public class FileSystemBusinessUnitLogoStorage implements BusinessUnitLogoStorage {

    private static final Map<String, String> EXTENSIONS =
            Map.of("image/png", "png", "image/jpeg", "jpg", "image/webp", "webp");

    private final Path root;

    public FileSystemBusinessUnitLogoStorage(@Value("${app.storage.path}") String storagePath) {
        this.root = Paths.get(storagePath).toAbsolutePath().normalize();
    }

    @Override
    public StoredLogo store(UUID tenantId, byte[] content, String contentType) {
        String fileName = UUID.randomUUID() + "." + EXTENSIONS.getOrDefault(contentType, "png");
        Path target = tenantDir(tenantId).resolve(fileName);
        try {
            Files.createDirectories(target.getParent());
            Files.write(target, content);
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao gravar o logo", e);
        }
        String key = "logos/" + tenantId + "/" + fileName;
        return new StoredLogo(key, "/api/public/logos/" + tenantId + "/" + fileName);
    }

    @Override
    public Optional<LogoFile> load(UUID tenantId, String fileName) {
        Path file = tenantDir(tenantId).resolve(fileName).normalize();
        if (!file.startsWith(tenantDir(tenantId)) || !Files.isRegularFile(file)) {
            return Optional.empty();
        }
        try {
            return Optional.of(new LogoFile(Files.readAllBytes(file), contentTypeOf(fileName)));
        } catch (IOException e) {
            if (log.isWarnEnabled()) {
                log.warn("Falha ao ler logo {}: {}", file, e.getMessage());
            }
            return Optional.empty();
        }
    }

    @Override
    public void delete(String key) {
        if (key == null) {
            return;
        }
        Path file = root.resolve(key).normalize();
        if (!file.startsWith(root)) {
            return;
        }
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            if (log.isWarnEnabled()) {
                log.warn("Falha ao apagar logo {}: {}", file, e.getMessage());
            }
        }
    }

    private Path tenantDir(UUID tenantId) {
        return root.resolve("logos").resolve(tenantId.toString());
    }

    private static String contentTypeOf(String fileName) {
        if (fileName.endsWith(".jpg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/png";
    }
}
