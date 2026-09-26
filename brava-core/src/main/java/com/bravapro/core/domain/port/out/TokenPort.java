package com.bravapro.core.domain.port.out;

public interface TokenPort {
    /**
     * Access token com a camada "grossa" de acesso (admin) e o tenant — as permissões finas nunca
     * vão no token (padrão Pontta), são resolvidas por requisição.
     */
    String generateAccessToken(String email, boolean admin, String tenantId, int tokenVersion);

    /**
     * {@code supportTenantId} só é preenchido quando a equipe da plataforma entra numa assinatura
     * que não é a dela: o refresh mantém a sessão de suporte naquela assinatura.
     */
    String generateRefreshToken(String email, String supportTenantId, int tokenVersion);

    String extractEmail(String token);

    String extractTenantId(String token);

    Integer extractTokenVersion(String token);

    boolean isTokenValid(String token);

    boolean isRefreshTokenValid(String token);
}
