package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.*;
import com.lashmanager.app.domain.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/anamnese")
@RequiredArgsConstructor
public class PublicAnamneseController {

    private final GetAnamneseByTokenUseCase getAnamneseByTokenUseCase;
    private final SubmitAnamneseByTokenUseCase submitAnamneseByTokenUseCase;

    @GetMapping("/{token}")
    public ResponseEntity<AnamnesePublicResponse> getByToken(@PathVariable String token) {
        var result = getAnamneseByTokenUseCase.execute(token);
        AnamneseResponse anamneseResponse = result.anamnese() != null ? toAnamneseResponse(result.anamnese()) : null;
        return ResponseEntity.ok(new AnamnesePublicResponse(
                result.clientName(), result.clientPhone(), anamneseResponse));
    }

    @PostMapping("/{token}")
    public ResponseEntity<Void> submit(
            @PathVariable String token,
            @RequestBody SubmitAnamneseRequest request) {
        submitAnamneseByTokenUseCase.execute(new SubmitAnamneseByTokenUseCase.SubmitAnamneseCommand(
                token,
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
        return ResponseEntity.ok().build();
    }

    private AnamneseResponse toAnamneseResponse(GetOrCreateAnamneseUseCase.AnamneseResult r) {
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
