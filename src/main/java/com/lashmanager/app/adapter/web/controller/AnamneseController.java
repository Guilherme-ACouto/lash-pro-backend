package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.*;
import com.lashmanager.app.domain.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/anamnese")
@RequiredArgsConstructor
public class AnamneseController {

    private final GetOrCreateAnamneseUseCase getOrCreateAnamneseUseCase;
    private final SaveAnamneseUseCase saveAnamneseUseCase;
    private final ListAnamneseSummaryUseCase listAnamneseSummaryUseCase;
    private final GenerateAnamneseLinkUseCase generateAnamneseLinkUseCase;

    @GetMapping
    public ResponseEntity<Page<AnamneseSummaryResponse>> list(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(listAnamneseSummaryUseCase.execute(search, pageable)
                .map(r -> new AnamneseSummaryResponse(
                        r.clientId() != null ? r.clientId().toString() : null,
                        r.clientName(), r.hasAnamnese(), r.updatedAt())));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<AnamneseResponse> get(@PathVariable UUID clientId) {
        var result = getOrCreateAnamneseUseCase.execute(clientId);
        return ResponseEntity.ok(toResponse(result));
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<AnamneseResponse> save(
            @PathVariable UUID clientId,
            @RequestBody SaveAnamneseRequest request) {
        var result = saveAnamneseUseCase.execute(new SaveAnamneseUseCase.SaveAnamneseCommand(
                clientId,
                request.guardianName(), request.address(), request.neighborhood(),
                request.city(), request.state(), request.birthDate(),
                request.phone(), request.cpf(), request.rg(),
                request.hadLashExtensions(), request.wearsMascara(),
                request.hasAllergies(), request.hasThyroidIssues(),
                request.sleepSide(), request.hadEyeProcedure(),
                request.isPregnantOrNursing(), request.hadOncologicalTreatment(),
                request.hasSkinDisease(), request.hasHealthTreatment(),
                request.usesMedication(), request.termAccepted()
        ));
        return ResponseEntity.ok(toResponse(result));
    }

    @PostMapping("/{clientId}/link")
    public ResponseEntity<GenerateLinkResponse> generateLink(@PathVariable UUID clientId) {
        String url = generateAnamneseLinkUseCase.execute(clientId);
        return ResponseEntity.ok(new GenerateLinkResponse(url));
    }

    private AnamneseResponse toResponse(GetOrCreateAnamneseUseCase.AnamneseResult r) {
        return new AnamneseResponse(
                r.id() != null ? r.id().toString() : null,
                r.clientId() != null ? r.clientId().toString() : null,
                r.clientName(), r.guardianName(), r.address(), r.neighborhood(),
                r.city(), r.state(), r.birthDate(), r.phone(), r.cpf(), r.rg(),
                r.hadLashExtensions(), r.wearsMascara(), r.hasAllergies(),
                r.hasThyroidIssues(), r.sleepSide(), r.hadEyeProcedure(),
                r.isPregnantOrNursing(), r.hadOncologicalTreatment(),
                r.hasSkinDisease(), r.hasHealthTreatment(), r.usesMedication(),
                r.termAccepted(), r.termAcceptedAt(), r.createdAt(), r.updatedAt()
        );
    }
}
