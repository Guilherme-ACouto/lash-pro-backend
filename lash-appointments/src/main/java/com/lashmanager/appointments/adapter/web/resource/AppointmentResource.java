package com.lashmanager.appointments.adapter.web.resource;

import com.lashmanager.appointments.application.command.CancelAppointmentCommand;
import com.lashmanager.appointments.application.command.CompleteAppointmentCommand;
import com.lashmanager.appointments.application.command.ConfirmAppointmentCommand;
import com.lashmanager.appointments.application.command.CreateAppointmentCommand;
import com.lashmanager.appointments.application.command.NoShowAppointmentCommand;
import com.lashmanager.appointments.application.command.UpdateAppointmentCommand;
import com.lashmanager.appointments.application.service.AppointmentApplicationService;
import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentDetails;
import com.lashmanager.appointments.domain.port.in.AppointmentQueryService;
import com.lashmanager.core.infrastructure.web.RestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentResource {

    private static final String ENTITY_NAME = "appointment";

    private final AppointmentApplicationService appointmentApplicationService;
    private final AppointmentQueryService appointmentQueryService;

    @GetMapping
    public ResponseEntity<List<AppointmentDetails>> listByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (endDate != null) {
            LocalDate startDate = date != null ? date : LocalDate.now();
            return ResponseEntity.ok(appointmentQueryService.listByDateRange(startDate, endDate));
        }
        LocalDate targetDate = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(appointmentQueryService.listByDate(targetDate));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateAppointmentCommand command) {
        Appointment appointment = appointmentApplicationService.when(command);
        return RestUtils.message().created(ENTITY_NAME, appointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetails> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentQueryService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateAppointmentCommand command) {
        appointmentApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable UUID id) {
        appointmentApplicationService.when(new ConfirmAppointmentCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(
            @PathVariable UUID id, @RequestBody(required = false) CompleteAppointmentCommand command) {
        CompleteAppointmentCommand toApply = command != null ? command : new CompleteAppointmentCommand(null, null);
        appointmentApplicationService.when(toApply.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        appointmentApplicationService.when(new CancelAppointmentCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PatchMapping("/{id}/no-show")
    public ResponseEntity<Void> noShow(@PathVariable UUID id) {
        appointmentApplicationService.when(new NoShowAppointmentCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }
}
