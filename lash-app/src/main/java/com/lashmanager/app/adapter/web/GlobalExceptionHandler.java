package com.lashmanager.app.adapter.web;

import com.lashmanager.appointments.domain.exception.AppointmentConflictException;
import com.lashmanager.appointments.domain.exception.AppointmentNotFoundException;
import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.core.adapter.web.dto.ErrorResponse;
import com.lashmanager.core.domain.exception.AccountNotActivatedException;
import com.lashmanager.core.domain.exception.ActivationKeyExpiredException;
import com.lashmanager.core.domain.exception.ActivationKeyInvalidException;
import com.lashmanager.core.domain.exception.BusinessException;
import com.lashmanager.core.domain.exception.DomainException;
import com.lashmanager.core.domain.exception.EmailAlreadyInUseException;
import com.lashmanager.core.domain.exception.InvalidCredentialsException;
import com.lashmanager.core.domain.exception.PlatformAdminRequiredException;
import com.lashmanager.core.domain.exception.SchemaProvisioningException;
import com.lashmanager.core.domain.exception.TenantInactiveException;
import com.lashmanager.core.domain.exception.TenantNotFoundException;
import com.lashmanager.core.domain.exception.TokenExpiredException;
import com.lashmanager.core.domain.exception.UserNotFoundException;
import com.lashmanager.fichas.domain.exception.ClientAlreadyHasFichaException;
import com.lashmanager.fichas.domain.exception.FichaNotFoundException;
import com.lashmanager.fichas.domain.exception.LashMappingNotFoundException;
import com.lashmanager.finance.domain.exception.FinancialEntryLinkedToAppointmentException;
import com.lashmanager.finance.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.services.domain.exception.ServiceAlreadyExistsException;
import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.stock.domain.exception.InventoryItemCodeAlreadyExistsException;
import com.lashmanager.stock.domain.exception.InventoryItemHasMovementsException;
import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ── Autenticação / JWT ────────────────────────────────────────────────────

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return err(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpired(TokenExpiredException ex) {
        return err(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccountNotActivatedException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotActivated(AccountNotActivatedException ex) {
        return err(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(TenantInactiveException.class)
    public ResponseEntity<ErrorResponse> handleTenantInactive(TenantInactiveException ex) {
        return err(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // ── Administração de tenants ──────────────────────────────────────────────

    @ExceptionHandler(PlatformAdminRequiredException.class)
    public ResponseEntity<ErrorResponse> handlePlatformAdminRequired(PlatformAdminRequiredException ex) {
        return err(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTenantNotFound(TenantNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ── Registro / Ativação (multi-tenancy) ──────────────────────────────────

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ActivationKeyInvalidException.class)
    public ResponseEntity<ErrorResponse> handleActivationKeyInvalid(ActivationKeyInvalidException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ActivationKeyExpiredException.class)
    public ResponseEntity<ErrorResponse> handleActivationKeyExpired(ActivationKeyExpiredException ex) {
        return err(HttpStatus.GONE, ex.getMessage());
    }

    @ExceptionHandler(SchemaProvisioningException.class)
    public ResponseEntity<ErrorResponse> handleSchemaProvisioning(SchemaProvisioningException ex) {
        log.error("Falha no provisionamento de schema: ", ex);
        return err(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao provisionar ambiente — tente novamente em instantes");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return err(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ── Clientes ──────────────────────────────────────────────────────────────

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleClientAlreadyExists(ClientAlreadyExistsException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(HasFutureAppointmentsException.class)
    public ResponseEntity<Map<String, Object>> handleHasFutureAppointments(HasFutureAppointmentsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 409);
        body.put("message", ex.getMessage());
        body.put("appointments", ex.getAppointments());
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // ── Serviços ──────────────────────────────────────────────────────────────

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleServiceNotFound(ServiceNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ServiceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleServiceAlreadyExists(ServiceAlreadyExistsException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Agendamentos ──────────────────────────────────────────────────────────

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentConflict(AppointmentConflictException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Financeiro ────────────────────────────────────────────────────────────

    @ExceptionHandler(FinancialEntryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFinancialEntryNotFound(FinancialEntryNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(FinancialEntryLinkedToAppointmentException.class)
    public ResponseEntity<ErrorResponse> handleFinancialEntryLinked(FinancialEntryLinkedToAppointmentException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Estoque ───────────────────────────────────────────────────────────────

    @ExceptionHandler(InventoryItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemNotFound(InventoryItemNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InventoryItemCodeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemCodeExists(InventoryItemCodeAlreadyExistsException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InventoryItemHasMovementsException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemHasMovements(InventoryItemHasMovementsException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Fichas ────────────────────────────────────────────────────────────────

    @ExceptionHandler(FichaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFichaNotFound(FichaNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LashMappingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLashMappingNotFound(LashMappingNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyHasFichaException.class)
    public ResponseEntity<ErrorResponse> handleClientAlreadyHasFicha(ClientAlreadyHasFichaException ex) {
        return err(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Genéricos ─────────────────────────────────────────────────────────────

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        return err(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex) {
        return err(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return err(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Erro interno não tratado: ", ex);
        return err(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
    }

    private ResponseEntity<ErrorResponse> err(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(
                        status.value(), message, LocalDateTime.now().toString()));
    }
}
