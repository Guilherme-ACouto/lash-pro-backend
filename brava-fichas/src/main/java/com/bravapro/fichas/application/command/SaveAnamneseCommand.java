package com.bravapro.fichas.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Upsert — o {@code clientId} não vem no corpo do JSON (vem do {@code @PathVariable} da URL na
 * rota autenticada, ou é resolvido a partir do token na rota pública), setado via
 * {@link #clientId(UUID)}.
 */
@CommandPermission(Permission.RECORD_UPDATE)
@Getter
@AllArgsConstructor
public class SaveAnamneseCommand extends AbstractCommand {

    private UUID clientId;

    private final String guardianName;
    private final String address;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final LocalDate birthDate;
    private final String phone;
    private final String cpf;
    private final String rg;
    private final boolean hadLashExtensions;
    private final boolean wearsMascara;
    private final boolean hasAllergies;
    private final boolean hasThyroidIssues;
    private final String sleepSide;
    private final boolean hadEyeProcedure;
    private final boolean isPregnantOrNursing;
    private final boolean hadOncologicalTreatment;
    private final boolean hasSkinDisease;
    private final boolean hasHealthTreatment;
    private final boolean usesMedication;
    private final boolean termAccepted;

    public SaveAnamneseCommand clientId(UUID clientId) {
        this.clientId = clientId;
        return this;
    }
}
