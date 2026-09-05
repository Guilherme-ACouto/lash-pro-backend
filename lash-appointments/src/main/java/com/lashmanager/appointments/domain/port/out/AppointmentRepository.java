package com.lashmanager.appointments.domain.port.out;

import com.lashmanager.appointments.domain.model.Appointment;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura para grade/listagem por data foi separada para
 * AppointmentQueryRepository (RBK-27) — findById continua aqui porque os use cases de escrita
 * (Update, ChangeStatus) precisam do agregado completo.
 */
public interface AppointmentRepository {
  Appointment save(Appointment appointment);

  Optional<Appointment> findById(UUID id);

  List<Appointment> findActiveByDate(LocalDate date);

  List<AppointmentSummary> findFutureActiveByClientId(UUID clientId, LocalDate from);

  void deleteFutureAppointmentsByClientId(UUID clientId, LocalDate from);

  void unlinkClientFromPastAppointments(UUID clientId, LocalDate from);

  boolean hasActiveAppointmentsByServiceId(UUID serviceId, LocalDate from);
}
