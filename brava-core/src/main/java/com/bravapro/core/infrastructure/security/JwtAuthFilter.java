package com.bravapro.core.infrastructure.security;

import com.bravapro.core.application.usecase.PlatformAdminChecker;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Autentica pelo JWT carregando o usuário do banco a cada requisição — por isso inativação e
 * "encerrar sessões" ({@code tokenVersion}) valem na hora, sem esperar o token expirar.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PlatformAdminChecker platformAdminChecker;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            if (jwtService.isTokenValid(token)) {
                String email = jwtService.extractEmail(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    authenticate(request, token, email);
                }
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Falha na autenticação JWT: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request, String token, String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (!(userDetails instanceof AuthenticatedUser user) || !user.isEnabled()) {
            return;
        }

        Integer tokenVersion = jwtService.extractTokenVersion(token);
        if ((tokenVersion != null ? tokenVersion : 0) != user.getTokenVersion()) {
            return;
        }

        String tenantClaim = jwtService.extractTenantId(token);
        UUID activeTenantId = tenantClaim != null ? UUID.fromString(tenantClaim) : user.getOwnTenantId();
        if (!activeTenantId.equals(user.getOwnTenantId()) && !platformAdminChecker.isPlatformAdmin(email)) {
            return;
        }

        AuthenticatedUser principal = user.inTenant(activeTenantId);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
