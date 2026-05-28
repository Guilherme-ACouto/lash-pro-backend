package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.*;
import com.lashmanager.app.domain.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/mappings")
@RequiredArgsConstructor
public class LashMappingController {

    private final CreateLashMappingUseCase createLashMappingUseCase;
    private final UpdateLashMappingUseCase updateLashMappingUseCase;
    private final GetLashMappingUseCase getLashMappingUseCase;
    private final ListMappingsByClientUseCase listMappingsByClientUseCase;
    private final ListMappingSummaryUseCase listMappingSummaryUseCase;
    private final DeleteLashMappingUseCase deleteLashMappingUseCase;

    @GetMapping
    public ResponseEntity<Page<MappingSummaryResponse>> list(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(listMappingSummaryUseCase.execute(search, pageable)
                .map(r -> new MappingSummaryResponse(r.clientId(), r.clientName(), r.mappingCount(), r.lastMappingDate())));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<MappingResponse>> listByClient(
            @PathVariable UUID clientId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(listMappingsByClientUseCase.execute(clientId, pageable)
                .map(this::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MappingResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(toResponse(getLashMappingUseCase.execute(id)));
    }

    @PostMapping("/client/{clientId}")
    public ResponseEntity<MappingResponse> create(
            @PathVariable UUID clientId,
            @RequestBody CreateMappingRequest request) {
        var result = createLashMappingUseCase.execute(new CreateLashMappingUseCase.CreateMappingCommand(
                clientId,
                request.mappingDate() != null ? request.mappingDate() : LocalDate.now(),
                request.mappingType(), request.curvature(), request.humidity(),
                request.temperature(), request.thickness(), request.threadBrand(),
                request.threadFormat(), request.adhesive(), request.lengthsUsed(),
                request.observations(), request.canvasData(),
                request.photoBefore(), request.photoAfter()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MappingResponse> update(
            @PathVariable UUID id,
            @RequestBody CreateMappingRequest request) {
        var result = updateLashMappingUseCase.execute(new UpdateLashMappingUseCase.UpdateMappingCommand(
                id,
                request.mappingDate(), request.mappingType(), request.curvature(),
                request.humidity(), request.temperature(), request.thickness(),
                request.threadBrand(), request.threadFormat(), request.adhesive(),
                request.lengthsUsed(), request.observations(), request.canvasData(),
                request.photoBefore(), request.photoAfter()
        ));
        return ResponseEntity.ok(toResponse(result));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        deleteLashMappingUseCase.execute(id);
    }

    private MappingResponse toResponse(CreateLashMappingUseCase.MappingResult r) {
        return new MappingResponse(
                r.id(), r.clientId(), r.clientName(), r.mappingDate(),
                r.mappingType(), r.curvature(), r.humidity(), r.temperature(),
                r.thickness(), r.threadBrand(), r.threadFormat(), r.adhesive(),
                r.lengthsUsed(), r.observations(), r.canvasData(),
                r.photoBefore(), r.photoAfter(), r.createdAt(), r.updatedAt()
        );
    }
}
