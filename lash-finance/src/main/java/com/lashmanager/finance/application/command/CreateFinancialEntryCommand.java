package com.lashmanager.finance.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFinancialEntryCommand extends AbstractCommand {

    @NotBlank
    private final String type;

    private final String expenseType;

    @NotBlank
    private final String description;

    @NotNull
    @DecimalMin("0.01")
    private final BigDecimal amount;

    @NotNull
    private final LocalDate dueDate;

    private final LocalDate paymentDate;
    private final String category;
    private final String paymentMethod;
    private final String receivedFrom;
    private final String notes;
}
