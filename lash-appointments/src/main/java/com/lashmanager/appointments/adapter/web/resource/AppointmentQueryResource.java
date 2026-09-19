package com.lashmanager.appointments.adapter.web.resource;

import com.lashmanager.appointments.domain.model.AppointmentDetails;
import com.lashmanager.appointments.domain.port.in.AppointmentQueryService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Só leitura — comando mora em {@link AppointmentResource} (mesma URL base). */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentQueryResource {

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

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetails> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentQueryService.getById(id));
    }
}
