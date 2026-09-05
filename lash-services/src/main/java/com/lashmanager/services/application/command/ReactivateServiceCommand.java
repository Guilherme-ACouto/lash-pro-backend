package com.lashmanager.services.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReactivateServiceCommand extends AbstractCommand {

    @NotNull
    private UUID id;
}
