package com.lashmanager.fichas.adapter.web.resource;

import com.lashmanager.fichas.domain.model.AnamnesePublicView;
import com.lashmanager.fichas.domain.port.in.PublicAnamneseQueryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura, sem autenticação — comando mora em {@link PublicAnamneseResource}. */
@RestController
@RequestMapping("/api/public/anamnese")
@RequiredArgsConstructor
public class PublicAnamneseQueryResource {

    private final PublicAnamneseQueryService publicAnamneseQueryService;

    @GetMapping("/{token}")
    public ResponseEntity<AnamnesePublicView> getByToken(@PathVariable String token) {
        return ResponseEntity.ok(publicAnamneseQueryService.getByToken(token));
    }
}
