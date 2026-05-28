package com.lashmanager.app.domain.port.in;

public interface RefreshTokenUseCase {

    record RefreshResponse(String accessToken) {}

    RefreshResponse execute(String refreshToken);
}
