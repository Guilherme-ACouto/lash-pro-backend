package com.bravapro.settings.adapter.web.resource;

import com.bravapro.settings.domain.port.in.LogoQueryService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Público ({@code /api/public/**}): o logo precisa carregar em {@code <img>} (sem header de
 * autenticação) e na ficha pública de anamnese. O arquivo tem nome aleatório e troca a cada upload,
 * então dá pra cachear por bastante tempo.
 */
@RestController
@RequestMapping("/api/public/logos")
@RequiredArgsConstructor
public class PublicLogoQueryResource {

    private final LogoQueryService logoQueryService;

    @GetMapping("/{tenantId}/{fileName:.+}")
    public ResponseEntity<byte[]> get(@PathVariable UUID tenantId, @PathVariable String fileName) {
        return logoQueryService
                .load(tenantId, fileName)
                .map(logo -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(logo.contentType()))
                        .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic())
                        .body(logo.content()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
