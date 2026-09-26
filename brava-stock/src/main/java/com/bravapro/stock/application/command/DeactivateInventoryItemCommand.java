package com.bravapro.stock.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateInventoryItemCommand extends AbstractCommand {
    @NotNull
    private UUID id;
}
