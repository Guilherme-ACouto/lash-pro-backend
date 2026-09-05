package com.lashmanager.app.adapter.web;

import com.lashmanager.appointments.domain.exception.AppointmentConflictException;
import com.lashmanager.appointments.domain.exception.AppointmentNotFoundException;
import com.lashmanager.clients.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.core.adapter.web.dto.Error;
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
    public ResponseEntity<Error> handleInvalidCredentials(InvalidCredentialsException ex) {
        return err(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", ex.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Error> handleTokenExpired(TokenExpiredException ex) {
        return err(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFound(UserNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(AccountNotActivatedException.class)
    public ResponseEntity<Error> handleAccountNotActivated(AccountNotActivatedException ex) {
        return err(HttpStatus.FORBIDDEN, "ACCOUNT_NOT_ACTIVATED", ex.getMessage());
    }

    @ExceptionHandler(TenantInactiveException.class)
    public ResponseEntity<Error> handleTenantInactive(TenantInactiveException ex) {
        return err(HttpStatus.FORBIDDEN, "TENANT_INACTIVE", ex.getMessage());
    }

    // ── Administração de tenants ──────────────────────────────────────────────

    @ExceptionHandler(PlatformAdminRequiredException.class)
    public ResponseEntity<Error> handlePlatformAdminRequired(PlatformAdminRequiredException ex) {
        return err(HttpStatus.FORBIDDEN, "PLATFORM_ADMIN_REQUIRED", ex.getMessage());
    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<Error> handleTenantNotFound(TenantNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "TENANT_NOT_FOUND", ex.getMessage());
    }

    // ── Registro / Ativação (multi-tenancy) ──────────────────────────────────

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Error> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {
        return err(HttpStatus.CONFLICT, "EMAIL_ALREADY_IN_USE", ex.getMessage());
    }

    @ExceptionHandler(ActivationKeyInvalidException.class)
    public ResponseEntity<Error> handleActivationKeyInvalid(ActivationKeyInvalidException ex) {
        return err(HttpStatus.NOT_FOUND, "ACTIVATION_KEY_INVALID", ex.getMessage());
    }

    @ExceptionHandler(ActivationKeyExpiredException.class)
    public ResponseEntity<Error> handleActivationKeyExpired(ActivationKeyExpiredException ex) {
        return err(HttpStatus.GONE, "ACTIVATION_KEY_EXPIRED", ex.getMessage());
    }

    @ExceptionHandler(SchemaProvisioningException.class)
    public ResponseEntity<Error> handleSchemaProvisioning(SchemaProvisioningException ex) {
        log.error("Falha no provisionamento de schema: ", ex);
        return err(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "SCHEMA_PROVISIONING_FAILED",
                "Falha ao provisionar ambiente — tente novamente em instantes");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolation(ConstraintViolationException ex) {
        return err(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", ex.getMessage());
    }

    // ── Clientes ──────────────────────────────────────────────────────────────

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Error> handleClientNotFound(ClientNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "CLIENT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<Error> handleClientAlreadyExists(ClientAlreadyExistsException ex) {
        return err(HttpStatus.CONFLICT, "CLIENT_ALREADY_EXISTS", ex.getMessage());
    }

    /**
     * Único caso que usa o campo {@code details} do {@link Error}: o frontend
     * ({@code AppointmentsWarningDialogComponent}) precisa da lista de agendamentos futuros pra
     * montar o modal de confirmação — não cabe numa mensagem de texto simples.
     */
    @ExceptionHandler(HasFutureAppointmentsException.class)
    public ResponseEntity<Error> handleHasFutureAppointments(HasFutureAppointmentsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Error.builder()
                        .code("HAS_FUTURE_APPOINTMENTS")
                        .message(ex.getMessage())
                        .details(ex.getAppointments())
                        .build());
    }

    // ── Serviços ──────────────────────────────────────────────────────────────

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Error> handleServiceNotFound(ServiceNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "SERVICE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(ServiceAlreadyExistsException.class)
    public ResponseEntity<Error> handleServiceAlreadyExists(ServiceAlreadyExistsException ex) {
        return err(HttpStatus.CONFLICT, "SERVICE_ALREADY_EXISTS", ex.getMessage());
    }

    // ── Agendamentos ──────────────────────────────────────────────────────────

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Error> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "APPOINTMENT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<Error> handleAppointmentConflict(AppointmentConflictException ex) {
        return err(HttpStatus.CONFLICT, "APPOINTMENT_CONFLICT", ex.getMessage());
    }

    // ── Financeiro ────────────────────────────────────────────────────────────

    @ExceptionHandler(FinancialEntryNotFoundException.class)
    public ResponseEntity<Error> handleFinancialEntryNotFound(FinancialEntryNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "FINANCIAL_ENTRY_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(FinancialEntryLinkedToAppointmentException.class)
    public ResponseEntity<Error> handleFinancialEntryLinked(FinancialEntryLinkedToAppointmentException ex) {
        return err(HttpStatus.CONFLICT, "FINANCIAL_ENTRY_LINKED_TO_APPOINTMENT", ex.getMessage());
    }

    // ── Estoque ───────────────────────────────────────────────────────────────

    @ExceptionHandler(InventoryItemNotFoundException.class)
    public ResponseEntity<Error> handleInventoryItemNotFound(InventoryItemNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "INVENTORY_ITEM_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(InventoryItemCodeAlreadyExistsException.class)
    public ResponseEntity<Error> handleInventoryItemCodeExists(InventoryItemCodeAlreadyExistsException ex) {
        return err(HttpStatus.CONFLICT, "INVENTORY_ITEM_CODE_ALREADY_EXISTS", ex.getMessage());
    }

    @ExceptionHandler(InventoryItemHasMovementsException.class)
    public ResponseEntity<Error> handleInventoryItemHasMovements(InventoryItemHasMovementsException ex) {
        return err(HttpStatus.CONFLICT, "INVENTORY_ITEM_HAS_MOVEMENTS", ex.getMessage());
    }

    // ── Fichas ────────────────────────────────────────────────────────────────

    @ExceptionHandler(FichaNotFoundException.class)
    public ResponseEntity<Error> handleFichaNotFound(FichaNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "FICHA_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(LashMappingNotFoundException.class)
    public ResponseEntity<Error> handleLashMappingNotFound(LashMappingNotFoundException ex) {
        return err(HttpStatus.NOT_FOUND, "LASH_MAPPING_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyHasFichaException.class)
    public ResponseEntity<Error> handleClientAlreadyHasFicha(ClientAlreadyHasFichaException ex) {
        return err(HttpStatus.CONFLICT, "CLIENT_ALREADY_HAS_FICHA", ex.getMessage());
    }

    // ── Genéricos ─────────────────────────────────────────────────────────────

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Error> handleBusiness(BusinessException ex) {
        return err(HttpStatus.UNPROCESSABLE_ENTITY, "BUSINESS_ERROR", ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Error> handleDomain(DomainException ex) {
        return err(HttpStatus.BAD_REQUEST, "DOMAIN_ERROR", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return err(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGeneral(Exception ex) {
        log.error("Erro interno não tratado: ", ex);
        return err(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Erro interno do servidor");
    }

    private ResponseEntity<Error> err(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status)
                .body(Error.builder().code(code).message(message).build());
    }
}
