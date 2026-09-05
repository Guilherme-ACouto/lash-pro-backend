package com.lashmanager.stock.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.stock.domain.port.in.RegisterPurchaseUseCase;
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
public class RegisterPurchaseCommand extends AbstractCommand {

    @NotNull
    private UUID itemId;

    @NotNull
    @DecimalMin("0.001")
    private BigDecimal quantity;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitCost;

    private String supplier;

    @NotNull
    private LocalDate purchaseDate;

    @NotBlank
    private String paymentType;

    private LocalDate dueDate;
    private String notes;

    public RegisterPurchaseUseCase.RegisterPurchaseCommand toDomainCommand() {
        return new RegisterPurchaseUseCase.RegisterPurchaseCommand(
                itemId, quantity, unitCost, supplier, purchaseDate, paymentType, dueDate, notes);
    }
}
