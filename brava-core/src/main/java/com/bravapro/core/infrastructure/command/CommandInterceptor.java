package com.bravapro.core.infrastructure.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bravapro.core.domain.model.CommandAuditLog;
import com.bravapro.core.domain.port.out.CommandAuditLogRepository;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.infrastructure.security.PermissionEvaluator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Intercepta todo ApplicationService.when(AbstractCommand) por convenção de assinatura: valida
 * (Bean Validation), checa a {@link CommandPermission} do Command (padrão Pontta — antes de
 * qualquer regra de negócio), loga início/fim e grava auditoria — sucesso ou falha. Permissão
 * negada também é auditada como falha. Falha ao gravar auditoria nunca derruba a operação de
 * negócio em si — é registrada em log e seguida.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CommandInterceptor {

    private final Validator validator;
    private final CommandAuditLogRepository commandAuditLogRepository;
    private final ObjectMapper objectMapper;
    private final PermissionEvaluator permissionEvaluator;
    private final CurrentAccess currentAccess;

    @Around("execution(* com.bravapro..*.application.service.*ApplicationService.when(..))")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        AbstractCommand command = extractCommand(joinPoint);
        validate(command);

        long start = System.currentTimeMillis();
        try {
            checkPermission(command);
            Object result = joinPoint.proceed();
            audit(command, true);
            if (log.isInfoEnabled()) {
                log.info(
                        "Command {} executado em {}ms",
                        command.getClass().getSimpleName(),
                        System.currentTimeMillis() - start);
            }
            return result;
        } catch (Exception e) {
            audit(command, false);
            if (log.isWarnEnabled()) {
                log.warn("Command {} falhou: {}", command.getClass().getSimpleName(), e.getMessage());
            }
            throw e;
        }
    }

    private AbstractCommand extractCommand(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || !(args[0] instanceof AbstractCommand command)) {
            throw new IllegalStateException(
                    "ApplicationService.when(...) deve receber um AbstractCommand como primeiro argumento: "
                            + joinPoint.getSignature());
        }
        return command;
    }

    private void checkPermission(AbstractCommand command) {
        CommandPermission required = command.getClass().getAnnotation(CommandPermission.class);
        if (required != null) {
            permissionEvaluator.check(required);
        }
    }

    private void validate(AbstractCommand command) {
        Set<ConstraintViolation<AbstractCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new ConstraintViolationException(message, violations);
        }
    }

    private void audit(AbstractCommand command, boolean success) {
        try {
            String payload = objectMapper.writeValueAsString(command);
            commandAuditLogRepository.save(CommandAuditLog.builder()
                    .id(UUID.randomUUID())
                    .commandClass(command.getClass().getSimpleName())
                    .payloadJson(payload)
                    .userId(currentAccess.email())
                    .userName(currentAccess.userName())
                    .tenantId(currentAccess.tenantId())
                    .executedAt(LocalDateTime.now())
                    .success(success)
                    .build());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(
                        "Falha ao gravar auditoria do command {}: {}",
                        command.getClass().getSimpleName(),
                        e.getMessage());
            }
        }
    }

}
