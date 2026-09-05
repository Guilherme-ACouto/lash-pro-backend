package com.lashmanager.finance.infrastructure.adapter;

import com.lashmanager.appointments.domain.port.out.AppointmentFinancialPort;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.model.FinancialEntryStatus;
import com.lashmanager.finance.domain.model.FinancialEntryType;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentFinancialPortImpl implements AppointmentFinancialPort {

  private final FinancialEntryRepository financialEntryRepository;

  @Override
  public UUID createIncomeEntry(
      UUID appointmentId,
      String description,
      BigDecimal amount,
      LocalDate date,
      String paymentMethod) {
    FinancialEntry entry =
        FinancialEntry.builder()
            .id(UUID.randomUUID())
            .type(FinancialEntryType.INCOME)
            .description(description)
            .amount(amount)
            .dueDate(date)
            .paymentDate(date)
            .status(FinancialEntryStatus.PAID)
            .appointmentId(appointmentId)
            .category("Serviço")
            .paymentMethod(paymentMethod)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    return financialEntryRepository.save(entry).getId();
  }
}
