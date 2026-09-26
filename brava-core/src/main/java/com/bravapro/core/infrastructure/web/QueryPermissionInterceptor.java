package com.bravapro.core.infrastructure.web;

import com.bravapro.core.infrastructure.security.PermissionEvaluator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Avalia o {@code queryPermission()} de toda QueryResource antes do método rodar (papel do
 * {@code QueryPermissionEvaluator} da Pontta). A exceção sobe pro {@code GlobalExceptionHandler},
 * que responde 403 no formato {@code Error} padrão.
 */
@Component
@RequiredArgsConstructor
public class QueryPermissionInterceptor implements HandlerInterceptor {

    private final PermissionEvaluator permissionEvaluator;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (handler instanceof HandlerMethod method && method.getBean() instanceof QueryPermissionAware resource) {
            permissionEvaluator.check(resource.queryPermission());
        }
        return true;
    }
}
