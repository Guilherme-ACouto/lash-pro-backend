package com.lashmanager.finance.infrastructure.persistence.mapper;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryExpenseType;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.infrastructure.persistence.entity.FinancialEntryEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialEntryPersistenceMapper {

  public FinancialEntry toDomain(FinancialEntryEntity e) {
    return FinancialEntry.builder()
        .id(e.getId())
        .type(FinancialEntryType.valueOf(e.getType()))
        .expenseType(
            e.getExpenseType() != null
                ? FinancialEntryExpenseType.valueOf(e.getExpenseType())
                : null)
        .description(e.getDescription())
        .amount(e.getAmount())
        .dueDate(e.getDueDate())
        .paymentDate(e.getPaymentDate())
        .status(FinancialEntryStatus.valueOf(e.getStatus()))
        .appointmentId(e.getAppointmentId())
        .category(e.getCategory())
        .paymentMethod(e.getPaymentMethod())
        .receivedFrom(e.getReceivedFrom())
        .notes(e.getNotes())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .build();
  }

  public FinancialEntryEntity toEntity(FinancialEntry d) {
    return FinancialEntryEntity.builder()
        .id(d.getId())
        .type(d.getType().name())
        .expenseType(d.getExpenseType() != null ? d.getExpenseType().name() : null)
        .description(d.getDescription())
        .amount(d.getAmount())
        .dueDate(d.getDueDate())
        .paymentDate(d.getPaymentDate())
        .status(d.getStatus().name())
        .appointmentId(d.getAppointmentId())
        .category(d.getCategory())
        .paymentMethod(d.getPaymentMethod())
        .receivedFrom(d.getReceivedFrom())
        .notes(d.getNotes())
        .createdAt(d.getCreatedAt())
        .updatedAt(d.getUpdatedAt())
        .build();
  }
}
