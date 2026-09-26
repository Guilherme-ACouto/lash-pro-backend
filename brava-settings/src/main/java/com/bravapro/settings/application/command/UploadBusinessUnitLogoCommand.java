package com.bravapro.settings.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Montado pelo Resource a partir do upload multipart (não é um {@code @RequestBody} JSON). O
 * conteúdo do arquivo fica fora do payload da auditoria ({@code @JsonIgnore}).
 */
@CommandPermission(admin = true)
@Getter
@AllArgsConstructor
public class UploadBusinessUnitLogoCommand extends AbstractCommand {

    @NotNull
    private final UUID id;

    @JsonIgnore
    private final byte[] content;

    private final String contentType;

    private final String originalFileName;
}
