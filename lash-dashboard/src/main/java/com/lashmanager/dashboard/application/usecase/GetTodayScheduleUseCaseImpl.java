package com.lashmanager.dashboard.application.usecase;

import com.lashmanager.appointments.infrastructure.persistence.entity.AppointmentEntity;
import com.lashmanager.appointments.infrastructure.persistence.repository.AppointmentJpaRepository;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.dashboard.domain.port.in.GetTodayScheduleUseCase;
import com.lashmanager.services.infrastructure.persistence.repository.ServiceJpaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTodayScheduleUseCaseImpl implements GetTodayScheduleUseCase {

  private final AppointmentJpaRepository appointmentJpaRepository;
  private final ClientJpaRepository clientJpaRepository;
  private final ServiceJpaRepository serviceJpaRepository;

  @Override
  public List<ScheduleEntry> execute() {
    List<AppointmentEntity> todays = appointmentJpaRepository.findByDate(LocalDate.now());

    Map<UUID, String> clientNames =
        todays.stream()
            .filter(a -> a.getClientId() != null)
            .map(AppointmentEntity::getClientId)
            .distinct()
            .collect(
                Collectors.toMap(
                    id -> id,
                    id ->
                        clientJpaRepository
                            .findById(id)
                            .map(c -> c.getName())
                            .orElse("Cliente removido")));

    record SvcInfo(String name, BigDecimal price) {}
    Map<UUID, SvcInfo> serviceDetails =
        todays.stream()
            .map(AppointmentEntity::getServiceId)
            .distinct()
            .collect(
                Collectors.toMap(
                    id -> id,
                    id ->
                        serviceJpaRepository
                            .findById(id)
                            .map(s -> new SvcInfo(s.getName(), s.getPrice()))
                            .orElse(new SvcInfo("Serviço removido", null))));

    return todays.stream()
        .map(
            a -> {
              String clientName =
                  a.getClientId() != null ? clientNames.getOrDefault(a.getClientId(), "—") : "—";
              SvcInfo svc = serviceDetails.get(a.getServiceId());
              return new ScheduleEntry(
                  a.getId(),
                  clientName,
                  svc != null ? svc.name() : "—",
                  svc != null ? svc.price() : null,
                  a.getScheduledTime() != null ? a.getScheduledTime().toString() : null,
                  a.getStatus());
            })
        .collect(Collectors.toList());
  }
}
