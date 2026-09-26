package com.bravapro.fichas.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** O {@code clientId} não vem no corpo do JSON — vem do {@code @PathVariable}
 * ({@code POST /api/mappings/client/{clientId}}), setado via {@link #clientId(UUID)}. */
@CommandPermission(Permission.RECORD_CREATE)
@Getter
@AllArgsConstructor
public class CreateMappingCommand extends AbstractCommand {

    private UUID clientId;

    private final LocalDate mappingDate;
    private final String mappingType;
    private final String curvature;
    private final String humidity;
    private final String temperature;
    private final String thickness;
    private final String threadBrand;
    private final String threadFormat;
    private final String adhesive;
    private final String lengthsUsed;
    private final String observations;
    private final String canvasData;
    private final String photoBefore;
    private final String photoAfter;

    public CreateMappingCommand clientId(UUID clientId) {
        this.clientId = clientId;
        return this;
    }
}
