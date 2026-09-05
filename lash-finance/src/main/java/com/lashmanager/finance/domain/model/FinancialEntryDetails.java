package com.lashmanager.finance.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Projeção de leitura — o agregado {@code FinancialEntry} não tem {@code counterpart}
 * (nome de quem pagou/recebeu, resolvido via JOIN em {@code Appointment}→{@code Client} quando o
 * lançamento é automático) nem {@code linkedToAppointment} (derivado). Mora do lado de
 * {@code FinancialEntryQueryService}, nunca do lado de escrita — mesmo padrão de
 * {@code AppointmentDetails}.
 */
public record FinancialEntryDetails(
        UUID id,
        FinancialEntryType type,
        FinancialEntryExpenseType expenseType,
        String description,
        BigDecimal amount,
        LocalDate dueDate,
        LocalDate paymentDate,
        FinancialEntryStatus status,
        String category,
        String paymentMethod,
        String counterpart,
        String notes,
        boolean linkedToAppointment,
        UUID appointmentId) {}
