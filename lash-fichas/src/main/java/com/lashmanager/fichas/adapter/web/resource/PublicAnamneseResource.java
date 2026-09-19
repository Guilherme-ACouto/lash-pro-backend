package com.lashmanager.fichas.adapter.web.resource;

import com.lashmanager.fichas.application.command.SubmitAnamneseByTokenCommand;
import com.lashmanager.fichas.application.service.PublicAnamneseApplicationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Só comando, sem autenticação — {@code /api/public/**} já liberado no {@code SecurityConfig}
 * (ver {@link com.lashmanager.core.infrastructure.security.SecurityConfig}). Leitura mora em
 * {@link PublicAnamneseQueryResource}.
 */
@RestController
@RequestMapping("/api/public/anamnese")
@RequiredArgsConstructor
public class PublicAnamneseResource {

    private final PublicAnamneseApplicationService publicAnamneseApplicationService;

    @PostMapping("/{token}")
    public ResponseEntity<Void> submit(
            @PathVariable String token, @Valid @RequestBody SubmitAnamneseByTokenCommand command) {
        publicAnamneseApplicationService.when(command.token(token));
        return ResponseEntity.noContent().build();
    }
}
