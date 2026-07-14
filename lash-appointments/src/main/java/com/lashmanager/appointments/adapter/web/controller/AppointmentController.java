package com.lashmanager.appointments.adapter.web.controller;

import com.lashmanager.appointments.adapter.web.dto.AppointmentResponse;
import com.lashmanager.appointments.adapter.web.dto.CompleteAppointmentRequest;
import com.lashmanager.appointments.adapter.web.dto.CreateAppointmentRequest;
import com.lashmanager.appointments.adapter.web.dto.UpdateAppointmentRequest;
import com.lashmanager.appointments.domain.port.in.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (endDate != null) {
            LocalDate startDate = date != null ? date : LocalDate.now();
            return ResponseEntity.ok(
                    listAppointmentsByDateUseCase.executeRange(startDate, endDate).stream()
                            .map(AppointmentResponse::from).toList()
            );
        }
        LocalDate targetDate = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(
                listAppointmentsByDateUseCase.execute(targetDate).stream()
                        .map(AppointmentResponse::from).toList()
        );
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody CreateAppointmentRequest req) {
        var result = createAppointmentUseCase.execute(new CreateAppointmentUseCase.CreateAppointmentCommand(
                req.clientId(), req.serviceId(), req.scheduledDate(), req.scheduledTime(), req.durationMinutes(), req.notes()
        ));
        return ResponseEntity
                .created(URI.create("/api/appointments/" + result.id()))
                .body(AppointmentResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(AppointmentResponse.from(getAppointmentUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAppointmentRequest req
    ) {
        var result = updateAppointmentUseCase.execute(id, new UpdateAppointmentUseCase.UpdateAppointmentCommand(
                req.clientId(), req.serviceId(), req.scheduledDate(), req.scheduledTime(), req.durationMinutes(), req.notes()
        ));
        return ResponseEntity.ok(AppointmentResponse.from(result));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable UUID id) {
        changeAppointmentStatusUseCase.confirm(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(
            @PathVariable UUID id,
            @RequestBody(required = false) CompleteAppointmentRequest req
    ) {
        changeAppointmentStatusUseCase.complete(id, req != null ? req.paymentMethod() : null);
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
}
