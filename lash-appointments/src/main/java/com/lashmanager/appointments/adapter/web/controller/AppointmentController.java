package com.lashmanager.appointments.adapter.web.controller;

import com.lashmanager.appointments.adapter.web.dto.AppointmentResponse;
import com.lashmanager.appointments.adapter.web.dto.CompleteAppointmentRequest;
import com.lashmanager.appointments.adapter.web.dto.CreateAppointmentRequest;
import com.lashmanager.appointments.adapter.web.dto.UpdateAppointmentRequest;
import com.lashmanager.appointments.application.command.CancelAppointmentCommand;
import com.lashmanager.appointments.application.command.CompleteAppointmentCommand;
import com.lashmanager.appointments.application.command.ConfirmAppointmentCommand;
import com.lashmanager.appointments.application.command.CreateAppointmentCommand;
import com.lashmanager.appointments.application.command.NoShowAppointmentCommand;
import com.lashmanager.appointments.application.command.UpdateAppointmentCommand;
import com.lashmanager.appointments.application.service.ChangeAppointmentStatusApplicationService;
import com.lashmanager.appointments.application.service.CreateAppointmentApplicationService;
import com.lashmanager.appointments.application.service.UpdateAppointmentApplicationService;
import com.lashmanager.appointments.domain.port.in.GetAppointmentUseCase;
import com.lashmanager.appointments.domain.port.in.ListAppointmentsByDateUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final CreateAppointmentApplicationService createAppointmentApplicationService;
    private final UpdateAppointmentApplicationService updateAppointmentApplicationService;
    private final ChangeAppointmentStatusApplicationService changeAppointmentStatusApplicationService;
    private final GetAppointmentUseCase getAppointmentUseCase;
    private final ListAppointmentsByDateUseCase listAppointmentsByDateUseCase;

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> listByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (endDate != null) {
            LocalDate startDate = date != null ? date : LocalDate.now();
            return ResponseEntity.ok(listAppointmentsByDateUseCase.executeRange(startDate, endDate).stream()
                    .map(AppointmentResponse::from)
                    .toList());
        }
        LocalDate targetDate = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(listAppointmentsByDateUseCase.execute(targetDate).stream()
                .map(AppointmentResponse::from)
                .toList());
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody CreateAppointmentRequest req) {
        var result = createAppointmentApplicationService.when(new CreateAppointmentCommand(
                req.clientId(),
                req.serviceId(),
                req.scheduledDate(),
                req.scheduledTime(),
                req.durationMinutes(),
                req.notes()));
        return ResponseEntity.created(URI.create("/api/appointments/" + result.id()))
                .body(AppointmentResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(AppointmentResponse.from(getAppointmentUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(
            @PathVariable UUID id, @Valid @RequestBody UpdateAppointmentRequest req) {
        var result = updateAppointmentApplicationService.when(new UpdateAppointmentCommand(
                id,
                req.clientId(),
                req.serviceId(),
                req.scheduledDate(),
                req.scheduledTime(),
                req.durationMinutes(),
                req.notes()));
        return ResponseEntity.ok(AppointmentResponse.from(result));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable UUID id) {
        changeAppointmentStatusApplicationService.when(new ConfirmAppointmentCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(
            @PathVariable UUID id, @RequestBody(required = false) CompleteAppointmentRequest req) {
        changeAppointmentStatusApplicationService.when(
                new CompleteAppointmentCommand(id, req != null ? req.paymentMethod() : null));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        changeAppointmentStatusApplicationService.when(new CancelAppointmentCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/no-show")
    public ResponseEntity<Void> noShow(@PathVariable UUID id) {
        changeAppointmentStatusApplicationService.when(new NoShowAppointmentCommand(id));
        return ResponseEntity.noContent().build();
    }
}
