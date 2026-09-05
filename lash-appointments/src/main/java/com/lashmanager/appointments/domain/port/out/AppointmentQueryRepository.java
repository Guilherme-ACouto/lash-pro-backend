package com.lashmanager.appointments.domain.port.out;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.model.AppointmentDetails;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de leitura — separada de AppointmentRepository (escrita) conforme RBK-27.
 * findByDateWithDetails/findByDateRangeWithDetails já retornam a projeção de leitura pronta
 * (AppointmentDetails, não o agregado Appointment) — mantido como está, sem reescrever para
 * Dao/RowMapper nativo agora (risco desnecessário sem poder compilar/testar; a query atual já
 * funciona).
 */
public interface AppointmentQueryRepository {
    Optional<Appointment> findById(UUID id);

    List<AppointmentDetails> findByDateWithDetails(LocalDate date);

    List<AppointmentDetails> findByDateRangeWithDetails(LocalDate startDate, LocalDate endDate);
}
