package com.bravapro.appointments.adapter.web.resource;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.web.QueryPermission;
import com.bravapro.core.infrastructure.web.QueryPermissionAware;
import com.bravapro.appointments.domain.model.AppointmentDetails;
import com.bravapro.appointments.domain.port.in.AppointmentQueryService;

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
public class AppointmentQueryResource implements QueryPermissionAware {

    private final AppointmentQueryService appointmentQueryService;

    @Override
    public QueryPermission queryPermission() {
        return QueryPermission.of(Permission.APPOINTMENT);
    }

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
