package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.AppointmentResponse;
import com.lashmanager.app.adapter.web.dto.CompleteAppointmentRequest;
import com.lashmanager.app.adapter.web.dto.CreateAppointmentRequest;
import com.lashmanager.app.adapter.web.dto.UpdateAppointmentRequest;
import com.lashmanager.app.domain.port.in.ChangeAppointmentStatusUseCase;
import com.lashmanager.app.domain.port.in.CreateAppointmentUseCase;
import com.lashmanager.app.domain.port.in.GetAppointmentUseCase;
import com.lashmanager.app.domain.port.in.ListAppointmentsByDateUseCase;
import com.lashmanager.app.domain.port.in.UpdateAppointmentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;
    private final GetAppointmentUseCase getAppointmentUseCase;
    private final ListAppointmentsByDateUseCase listAppointmentsByDateUseCase;
    private final ChangeAppointmentStatusUseCase changeAppointmentStatusUseCase;

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> listByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (endDate != null) {
            LocalDate startDate = date != null ? date : LocalDate.now();
            return ResponseEntity.ok(
                    listAppointmentsByDateUseCase.executeRange(startDate, endDate).stream()
                            .map(this::toResponse)
                            .toList()
            );
        }
        LocalDate targetDate = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(
                listAppointmentsByDateUseCase.execute(targetDate).stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody CreateAppointmentRequest req) {
        var result = createAppointmentUseCase.execute(toCommand(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(toResponse(getAppointmentUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAppointmentRequest req) {
        var result = updateAppointmentUseCase.execute(id, toUpdateCommand(req));
        return ResponseEntity.ok(toResponse(result));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable UUID id) {
        changeAppointmentStatusUseCase.confirm(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(
            @PathVariable UUID id,
            @RequestBody(required = false) CompleteAppointmentRequest req) {
        String paymentMethod = req != null ? req.paymentMethod() : null;
        changeAppointmentStatusUseCase.complete(id, paymentMethod);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        changeAppointmentStatusUseCase.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/no-show")
    public ResponseEntity<Void> noShow(@PathVariable UUID id) {
        changeAppointmentStatusUseCase.noShow(id);
        return ResponseEntity.noContent().build();
    }


    private CreateAppointmentUseCase.CreateAppointmentCommand toCommand(CreateAppointmentRequest req) {
        return new CreateAppointmentUseCase.CreateAppointmentCommand(
                req.clientId(), req.serviceId(), req.scheduledDate(),
                req.scheduledTime(), req.durationMinutes(), req.notes()
        );
    }

    private CreateAppointmentUseCase.CreateAppointmentCommand toUpdateCommand(UpdateAppointmentRequest req) {
        return new CreateAppointmentUseCase.CreateAppointmentCommand(
                req.clientId(), req.serviceId(), req.scheduledDate(),
                req.scheduledTime(), req.durationMinutes(), req.notes()
        );
    }

    private AppointmentResponse toResponse(CreateAppointmentUseCase.AppointmentResult r) {
        return new AppointmentResponse(
                r.id().toString(), r.clientId() != null ? r.clientId().toString() : null, r.clientName(),
                r.serviceId().toString(), r.serviceName(), r.servicePrice(),
                r.scheduledDate(), r.scheduledTime(), r.durationMinutes(),
                r.status(), r.notes(), r.financialEntryId(), r.createdAt()
        );
    }
}
