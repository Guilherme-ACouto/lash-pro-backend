package com.lashmanager.finance.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.finance.domain.port.in.UpdateFinancialEntryUseCase;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFinancialEntryCommand extends AbstractCommand {

    @NotNull
    private UUID id;

    @NotBlank
    private String type;

    private String expenseType;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private LocalDate dueDate;

    private LocalDate paymentDate;
    private String category;
    private String paymentMethod;
    private String receivedFrom;
    private String notes;

    public UpdateFinancialEntryUseCase.UpdateCommand toDomainCommand() {
        return new UpdateFinancialEntryUseCase.UpdateCommand(
                type,
                expenseType,
                description,
                amount,
                dueDate,
                paymentDate,
                category,
                paymentMethod,
                receivedFrom,
                notes);
    }
}
