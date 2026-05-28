package com.lashmanager.app.adapter.web.dto;
import java.time.LocalDate;

public record SaveAnamneseRequest(
    String guardianName,
    String address,
    String neighborhood,
    String city,
    String state,
    LocalDate birthDate,
    String phone,
    String cpf,
    String rg,
    boolean hadLashExtensions,
    boolean wearsMascara,
    boolean hasAllergies,
    boolean hasThyroidIssues,
    String sleepSide,
    boolean hadEyeProcedure,
    boolean isPregnantOrNursing,
    boolean hadOncologicalTreatment,
    boolean hasSkinDisease,
    boolean hasHealthTreatment,
    boolean usesMedication,
    boolean termAccepted
) {}
