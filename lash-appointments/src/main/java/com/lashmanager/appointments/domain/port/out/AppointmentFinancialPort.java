package com.lashmanager.appointments.domain.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface AppointmentFinancialPort {
    UUID createIncomeEntry(
            UUID appointmentId,
            String description,
            BigDecimal amount,
            LocalDate date,
            String paymentMethod
    );
}
