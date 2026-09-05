package com.lashmanager.finance.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code id} não vem no corpo do JSON — vem do {@code @PathVariable} da URL, setado via
 * {@link #id(UUID)} pelo Resource antes de repassar pra ApplicationService.
 */
@Getter
@AllArgsConstructor
public class UpdateFinancialEntryCommand extends AbstractCommand {

    private UUID id;

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

    public UpdateFinancialEntryCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
