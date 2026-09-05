package com.lashmanager.clients.domain.port.out;

import com.lashmanager.clients.domain.model.AppointmentSummary;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClientAppointmentPort {
    List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate from);

    void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate from);

    void unlinkClientFromPastAppointments(UUID clientId, LocalDate from);
}
