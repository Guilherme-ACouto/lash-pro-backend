package com.lashmanager.app.infrastructure.persistence.mapper;

import com.lashmanager.app.domain.model.FinancialEntry;
import com.lashmanager.app.domain.model.FinancialEntryExpenseType;
import com.lashmanager.app.domain.model.FinancialEntryStatus;
import com.lashmanager.app.domain.model.FinancialEntryType;
import com.lashmanager.app.infrastructure.persistence.entity.FinancialEntryEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialEntryMapper {

    public FinancialEntry toDomain(FinancialEntryEntity e) {
        return FinancialEntry.builder()
                .id(e.getId())
                .type(FinancialEntryType.valueOf(e.getType()))
                .description(e.getDescription())
                .amount(e.getAmount())
                .dueDate(e.getDueDate())
                .paymentDate(e.getPaymentDate())
                .status(FinancialEntryStatus.valueOf(e.getStatus()))
                .appointmentId(e.getAppointmentId())
                .category(e.getCategory())
                .notes(e.getNotes())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .paymentMethod(e.getPaymentMethod())
                .expenseType(e.getExpenseType() != null
                        ? FinancialEntryExpenseType.valueOf(e.getExpenseType())
                        : null)
                .receivedFrom(e.getReceivedFrom())
                .build();
    }

    public FinancialEntryEntity toEntity(FinancialEntry f) {
        return FinancialEntryEntity.builder()
                .id(f.getId())
                .type(f.getType().name())
                .description(f.getDescription())
                .amount(f.getAmount())
                .dueDate(f.getDueDate())
                .paymentDate(f.getPaymentDate())
                .status(f.getStatus().name())
                .appointmentId(f.getAppointmentId())
                .category(f.getCategory())
                .notes(f.getNotes())
                .createdAt(f.getCreatedAt())
                .paymentMethod(f.getPaymentMethod())
                .expenseType(f.getExpenseType() != null ? f.getExpenseType().name() : null)
                .receivedFrom(f.getReceivedFrom())
                .build();
    }
}
