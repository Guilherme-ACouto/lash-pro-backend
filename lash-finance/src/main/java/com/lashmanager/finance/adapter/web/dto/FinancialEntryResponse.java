package com.lashmanager.finance.adapter.web.dto;

import com.lashmanager.finance.domain.port.in.ListFinancialEntriesUseCase;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FinancialEntryResponse(
        UUID id,
        String type,
        String expenseType,
        String description,
        BigDecimal amount,
        LocalDate dueDate,
        LocalDate paymentDate,
        String status,
        String category,
        String paymentMethod,
        String counterpart,
        String notes,
        boolean linkedToAppointment,
        UUID appointmentId) {
    public static FinancialEntryResponse from(ListFinancialEntriesUseCase.EntryResult r) {
        return new FinancialEntryResponse(
                r.id(),
                r.type(),
                r.expenseType(),
                r.description(),
                r.amount(),
                r.dueDate(),
                r.paymentDate(),
                r.status(),
                r.category(),
                r.paymentMethod(),
                r.counterpart(),
                r.notes(),
                r.linkedToAppointment(),
                r.appointmentId());
    }
}
