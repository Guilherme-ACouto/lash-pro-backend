package com.bravapro.fichas.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** O {@code id} não vem no corpo do JSON — vem do {@code @PathVariable} da URL, setado via
 * {@link #id(UUID)} pelo Resource antes de repassar pra ApplicationService. */
@Getter
@AllArgsConstructor
public class UpdateMappingCommand extends AbstractCommand {

    private UUID id;

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

    public UpdateMappingCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
