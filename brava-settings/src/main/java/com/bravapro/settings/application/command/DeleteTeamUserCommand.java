package com.bravapro.settings.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Exclusão definitiva — só pra quem nunca fez nada no sistema (senão, inativar). */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTeamUserCommand extends AbstractCommand {

    @NotNull
    private UUID id;
}
