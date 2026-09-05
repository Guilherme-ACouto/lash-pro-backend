package com.lashmanager.core.infrastructure.security;

import com.lashmanager.core.infrastructure.multitenancy.TenantContext;
import com.lashmanager.core.infrastructure.multitenancy.TenantSchemaNaming;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Resolve o tenant da requisição a partir do claim tenantId do JWT e popula o TenantContext, que o
 * Hibernate consulta (via CurrentTenantIdentifierResolverImpl) para decidir o search_path da
 * conexão. Roda antes do JwtAuthFilter — precisa validar o token por conta própria, já que a
 * autenticação ainda não existe neste ponto da cadeia. Quando não há token/claim, o TenantContext
 * fica vazio e o resolver usa o default "public" (necessário para o login localizar o usuário).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TenantResolvingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TenantSchemaNaming tenantSchemaNaming;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            resolveTenant(request);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private void resolveTenant(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        try {
            if (!jwtService.isTokenValid(token)) {
                return;
            }
            String tenantIdClaim = jwtService.extractTenantId(token);
            if (tenantIdClaim != null) {
                String schemaName = tenantSchemaNaming.schemaNameFor(UUID.fromString(tenantIdClaim));
                TenantContext.setCurrentTenant(schemaName);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Falha ao resolver tenant do token: {}", e.getMessage());
            }
        }
    }
}
