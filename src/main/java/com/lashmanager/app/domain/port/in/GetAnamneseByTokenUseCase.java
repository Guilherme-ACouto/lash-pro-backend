package com.lashmanager.app.domain.port.in;

public interface GetAnamneseByTokenUseCase {
    record AnamneseTokenResult(
        String clientName, String clientPhone,
        GetOrCreateAnamneseUseCase.AnamneseResult anamnese
    ) {}

    AnamneseTokenResult execute(String token);
}
