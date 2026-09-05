package com.lashmanager.finance.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FinancialEntry {
    private UUID id;
    private FinancialEntryType type;
    private FinancialEntryExpenseType expenseType;
    private String description;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private FinancialEntryStatus status;
    private UUID appointmentId;
    private String category;
    private String paymentMethod;
    private String receivedFrom;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
