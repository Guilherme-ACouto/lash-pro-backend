package com.lashmanager.stock.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.stock.domain.port.in.RegisterManualExitUseCase;
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
public class RegisterManualExitCommand extends AbstractCommand {

  @NotNull private UUID itemId;

  @NotNull
  @DecimalMin("0.001")
  private BigDecimal quantity;

  @NotBlank private String reason;

  private String notes;

  @NotNull private LocalDate exitDate;

  public RegisterManualExitUseCase.RegisterManualExitCommand toDomainCommand() {
    return new RegisterManualExitUseCase.RegisterManualExitCommand(
        itemId, quantity, reason, notes, exitDate);
  }
}
