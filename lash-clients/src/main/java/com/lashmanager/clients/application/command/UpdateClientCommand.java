package com.lashmanager.clients.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code id} não vem no corpo do JSON (nunca é enviado pelo client) — o {@code @RequestBody}
 * desserializa só os campos de dado, e o Controller preenche o id (que vem do
 * {@code @PathVariable} da URL) chamando {@link #id(UUID)} antes de repassar pra
 * ApplicationService. Por isso {@code id} não tem validação de "não nulo": no momento em que o
 * Spring valida o {@code @Valid @RequestBody}, o id ainda não foi setado.
 */
@Getter
@AllArgsConstructor
public class UpdateClientCommand extends AbstractCommand {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 100)
    private final String name;

    @NotBlank
    @Size(min = 10, max = 20)
    private final String phone;

    private final String email;
    private final LocalDate birthDate;

    @Size(max = 500)
    private final String notes;

    public UpdateClientCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
