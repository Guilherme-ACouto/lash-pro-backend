package com.lashmanager.core.domain.port.in;

public interface RefreshTokenUseCase {

    record RefreshResponse(String accessToken) {}

    RefreshResponse execute(String refreshToken);
}
