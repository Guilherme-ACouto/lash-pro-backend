package com.bravapro.finance.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@CommandPermission(Permission.FINANCIAL_DELETE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFinancialEntryCommand extends AbstractCommand {
    @NotNull
    private UUID id;
}
