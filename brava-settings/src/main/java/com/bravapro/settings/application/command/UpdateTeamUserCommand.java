package com.bravapro.settings.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** O {@code id} vem da URL (ver {@link #id(UUID)}). E-mail não muda por aqui (é o login). */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeamUserCommand extends AbstractCommand {

    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 255)
    private String name;

    private boolean admin;

    private boolean professional;

    private Set<String> permissions;

    public UpdateTeamUserCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
