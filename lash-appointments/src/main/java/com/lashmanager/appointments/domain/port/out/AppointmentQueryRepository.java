package com.lashmanager.appointments.domain.port.out;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.appointments.domain.port.in.CreateAppointmentUseCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de leitura — separada de AppointmentRepository (escrita) conforme
 * RBK-27. findByDateWithDetails/findByDateRangeWithDetails já retornam o DTO
 * de leitura pronto (não a entidade de domínio) — mantido como está, sem
 * reescrever para Dao/RowMapper nativo agora (risco desnecessário sem poder
 * compilar/testar; a query atual já funciona).
 */
public interface AppointmentQueryRepository {
    Optional<Appointment> findById(UUID id);
    List<CreateAppointmentUseCase.AppointmentResult> findByDateWithDetails(LocalDate date);
    List<CreateAppointmentUseCase.AppointmentResult> findByDateRangeWithDetails(LocalDate startDate, LocalDate endDate);
}
