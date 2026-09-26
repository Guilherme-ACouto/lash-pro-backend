package com.bravapro.fichas.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Equivalente público (sem autenticação, {@code POST /api/public/anamnese/{token}}) do
 * {@link SaveAnamneseCommand} — Command próprio (em vez de reaproveitar o autenticado) porque o
 * {@code CommandInterceptor} intercepta por convenção de assinatura
 * ({@code ApplicationService.when(AbstractCommand)}); aqui quem identifica o cliente é o
 * {@code token} (setado via {@link #token(String)} pelo Resource a partir do path), não um
 * {@code clientId} de uma rota autenticada.
 */
@Getter
@AllArgsConstructor
public class SubmitAnamneseByTokenCommand extends AbstractCommand {

    private String token;

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

    public SubmitAnamneseByTokenCommand token(String token) {
        this.token = token;
        return this;
    }

    /** Converte pro mesmo Command usado na rota autenticada, uma vez que o clientId já foi
     * resolvido a partir do token — evita duplicar a lógica de atualização em Anamnese. */
    public SaveAnamneseCommand toSaveCommand(java.util.UUID clientId) {
        return new SaveAnamneseCommand(
                clientId,
                guardianName,
                address,
                neighborhood,
                city,
                state,
                birthDate,
                phone,
                cpf,
                rg,
                hadLashExtensions,
                wearsMascara,
                hasAllergies,
                hasThyroidIssues,
                sleepSide,
                hadEyeProcedure,
                isPregnantOrNursing,
                hadOncologicalTreatment,
                hasSkinDisease,
                hasHealthTreatment,
                usesMedication,
                termAccepted);
    }
}
