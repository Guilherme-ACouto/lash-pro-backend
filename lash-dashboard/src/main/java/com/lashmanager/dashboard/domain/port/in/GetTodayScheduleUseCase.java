package com.lashmanager.dashboard.domain.port.in;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GetTodayScheduleUseCase {

    record ScheduleEntry(
            UUID appointmentId,
            String clientName,
            String serviceName,
            BigDecimal servicePrice,
            String scheduledTime,
            String status) {}

    List<ScheduleEntry> execute();
}
