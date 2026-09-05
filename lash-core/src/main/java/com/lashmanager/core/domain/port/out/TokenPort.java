package com.lashmanager.core.domain.port.out;

public interface TokenPort {
  String generateAccessToken(String email, String role, String tenantId);

  String generateRefreshToken(String email);

  String extractEmail(String token);

  String extractTenantId(String token);

  boolean isTokenValid(String token);

  boolean isRefreshTokenValid(String token);
}
