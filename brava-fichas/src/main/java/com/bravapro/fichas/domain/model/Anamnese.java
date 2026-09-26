package com.bravapro.fichas.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bravapro.core.domain.model.DomainEntity;
import com.bravapro.fichas.application.command.SaveAnamneseCommand;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Anamnese implements DomainEntity {

    private static final SecureRandom TOKEN_RANDOM = new SecureRandom();

    private UUID id;
    private UUID clientId;
    private String clientName;

    private String guardianName;
    private String address;
    private String neighborhood;
    private String city;
    private String state;
    private LocalDate birthDate;
    private String phone;
    private String cpf;
    private String rg;

    private boolean hadLashExtensions;
    private boolean wearsMascara;
    private boolean hasAllergies;
    private boolean hasThyroidIssues;
    private SleepSide sleepSide;
    private boolean hadEyeProcedure;
    private boolean isPregnantOrNursing;
    private boolean hadOncologicalTreatment;
    private boolean hasSkinDisease;
    private boolean hasHealthTreatment;
    private boolean usesMedication;
    private boolean termAccepted;
    private LocalDateTime termAcceptedAt;

    private String linkToken;
    private LocalDateTime linkTokenExpiry;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(SaveAnamneseCommand command) {
        this.guardianName = command.getGuardianName();
        this.address = command.getAddress();
        this.neighborhood = command.getNeighborhood();
        this.city = command.getCity();
        this.state = command.getState();
        this.birthDate = command.getBirthDate();
        this.phone = command.getPhone();
        this.cpf = command.getCpf();
        this.rg = command.getRg();
        this.hadLashExtensions = command.isHadLashExtensions();
        this.wearsMascara = command.isWearsMascara();
        this.hasAllergies = command.isHasAllergies();
        this.hasThyroidIssues = command.isHasThyroidIssues();
        this.sleepSide = command.getSleepSide() != null ? SleepSide.valueOf(command.getSleepSide()) : null;
        this.hadEyeProcedure = command.isHadEyeProcedure();
        this.isPregnantOrNursing = command.isPregnantOrNursing();
        this.hadOncologicalTreatment = command.isHadOncologicalTreatment();
        this.hasSkinDisease = command.isHasSkinDisease();
        this.hasHealthTreatment = command.isHasHealthTreatment();
        this.usesMedication = command.isUsesMedication();
        this.termAccepted = command.isTermAccepted();
        this.termAcceptedAt = command.isTermAccepted() ? LocalDateTime.now() : this.termAcceptedAt;
        this.updatedAt = LocalDateTime.now();
    }

    public void generateLink(int expirationHours) {
        byte[] bytes = new byte[32];
        TOKEN_RANDOM.nextBytes(bytes);
        this.linkToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        this.linkTokenExpiry = LocalDateTime.now().plusHours(expirationHours);
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isLinkExpired() {
        return linkTokenExpiry == null || linkTokenExpiry.isBefore(LocalDateTime.now());
    }

    /**
     * Getter escrito à mão (Lombok detecta e não gera o dele) — o Jackson, por padrão, remove o
     * prefixo "is" de getters boolean ao serializar ({@code isPregnantOrNursing()} viraria
     * {@code "pregnantOrNursing"} no JSON). O frontend (já pronto, não pode mudar — ver spec de
     * anamnese) espera literalmente a chave {@code "isPregnantOrNursing"}, então força aqui.
     */
    @JsonProperty("isPregnantOrNursing")
    public boolean isPregnantOrNursing() {
        return isPregnantOrNursing;
    }
}
