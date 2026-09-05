package com.lashmanager.fichas.adapter.web.controller;

import com.lashmanager.fichas.adapter.web.dto.*;
import com.lashmanager.fichas.application.command.CreateFichaCommand;
import com.lashmanager.fichas.application.command.CreateLashMappingCommand;
import com.lashmanager.fichas.application.command.DeleteLashMappingCommand;
import com.lashmanager.fichas.application.command.UpdateFichaCommand;
import com.lashmanager.fichas.application.command.UpdateLashMappingCommand;
import com.lashmanager.fichas.application.service.CreateFichaApplicationService;
import com.lashmanager.fichas.application.service.LashMappingApplicationService;
import com.lashmanager.fichas.application.service.UpdateFichaApplicationService;
import com.lashmanager.fichas.domain.port.in.*;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FichaController {

  private final CreateFichaApplicationService createFichaApplicationService;
  private final UpdateFichaApplicationService updateFichaApplicationService;
  private final LashMappingApplicationService lashMappingApplicationService;
  private final GetFichaUseCase getFichaUseCase;
  private final ListFichasUseCase listFichasUseCase;
  private final ListLashMappingsUseCase listLashMappingsUseCase;

  // ── Fichas ──────────────────────────────────────────────────────────────

  @PostMapping
  public ResponseEntity<FichaResult> create(@Valid @RequestBody CreateFichaRequest req) {
    var result =
        createFichaApplicationService.when(
            new CreateFichaCommand(
                req.clientId(),
                req.date(),
                req.skinType(),
                req.eyeShape(),
                req.hasAllergies(),
                req.allergiesDescription(),
                req.hasMedications(),
                req.medicationsDescription(),
                req.hasSensitivities(),
                req.sensitivitiesDescription(),
                req.observations()));
    return ResponseEntity.status(201).body(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<FichaResult> update(
      @PathVariable UUID id, @Valid @RequestBody UpdateFichaRequest req) {
    var result =
        updateFichaApplicationService.when(
            new UpdateFichaCommand(
                id,
                req.date(),
                req.skinType(),
                req.eyeShape(),
                req.hasAllergies(),
                req.allergiesDescription(),
                req.hasMedications(),
                req.medicationsDescription(),
                req.hasSensitivities(),
                req.sensitivitiesDescription(),
                req.observations()));
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FichaResult> get(@PathVariable UUID id) {
    return ResponseEntity.ok(getFichaUseCase.execute(id));
  }

  @GetMapping("/client/{clientId}")
  public ResponseEntity<FichaResult> getByClient(@PathVariable UUID clientId) {
    return ResponseEntity.ok(getFichaUseCase.executeByClient(clientId));
  }

  @GetMapping
  public ResponseEntity<Page<FichaResult>> list(
      @RequestParam(required = false) String search, Pageable pageable) {
    return ResponseEntity.ok(
        listFichasUseCase.execute(new ListFichasUseCase.ListFichasQuery(search), pageable));
  }

  // ── Mapeamentos ──────────────────────────────────────────────────────────

  @PostMapping("/{fichaId}/mappings")
  public ResponseEntity<LashMappingResult> createMapping(
      @PathVariable UUID fichaId, @Valid @RequestBody CreateLashMappingRequest req) {
    var result =
        lashMappingApplicationService.when(
            new CreateLashMappingCommand(
                fichaId,
                req.appointmentId(),
                req.date(),
                req.technique(),
                req.curvature(),
                req.thickness(),
                req.length(),
                req.rightEyeNotes(),
                req.leftEyeNotes(),
                req.notes()));
    return ResponseEntity.status(201).body(result);
  }

  @PutMapping("/{fichaId}/mappings/{mappingId}")
  public ResponseEntity<LashMappingResult> updateMapping(
      @PathVariable UUID fichaId,
      @PathVariable UUID mappingId,
      @Valid @RequestBody UpdateLashMappingRequest req) {
    var result =
        lashMappingApplicationService.when(
            new UpdateLashMappingCommand(
                mappingId,
                req.date(),
                req.technique(),
                req.curvature(),
                req.thickness(),
                req.length(),
                req.rightEyeNotes(),
                req.leftEyeNotes(),
                req.notes()));
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{fichaId}/mappings")
  public ResponseEntity<Page<LashMappingResult>> listMappings(
      @PathVariable UUID fichaId, Pageable pageable) {
    return ResponseEntity.ok(listLashMappingsUseCase.execute(fichaId, pageable));
  }

  @DeleteMapping("/{fichaId}/mappings/{mappingId}")
  public ResponseEntity<Void> deleteMapping(
      @PathVariable UUID fichaId, @PathVariable UUID mappingId) {
    lashMappingApplicationService.when(new DeleteLashMappingCommand(mappingId));
    return ResponseEntity.noContent().build();
  }
}
