package com.lashmanager.app.adapter.web.controller;

import com.lashmanager.app.adapter.web.dto.ErrorResponse;
import com.lashmanager.app.domain.exception.AnamneseNotFoundException;
import com.lashmanager.app.domain.exception.AnamneseTokenAlreadyUsedException;
import com.lashmanager.app.domain.exception.AnamneseTokenExpiredException;
import com.lashmanager.app.domain.exception.AnamneseTokenNotFoundException;
import com.lashmanager.app.domain.exception.LashMappingNotFoundException;
import com.lashmanager.app.domain.exception.AppointmentConflictException;
import com.lashmanager.app.domain.exception.AppointmentNotFoundException;
import com.lashmanager.app.domain.exception.ClientAlreadyExistsException;
import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.DomainException;
import com.lashmanager.app.domain.exception.FinancialEntryLinkedToAppointmentException;
import com.lashmanager.app.domain.exception.FinancialEntryNotFoundException;
import com.lashmanager.app.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.app.domain.exception.InvalidCredentialsException;
import com.lashmanager.app.domain.exception.InventoryItemCodeAlreadyExistsException;
import com.lashmanager.app.domain.exception.InventoryItemHasMovementsException;
import com.lashmanager.app.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.app.domain.exception.ServiceNotFoundException;
import com.lashmanager.app.domain.exception.TokenExpiredException;
import com.lashmanager.app.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpired(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleServiceNotFound(ServiceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleClientAlreadyExists(ClientAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentConflict(AppointmentConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(FinancialEntryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFinancialEntryNotFound(FinancialEntryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(FinancialEntryLinkedToAppointmentException.class)
    public ResponseEntity<ErrorResponse> handleFinancialEntryLinked(FinancialEntryLinkedToAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(HasFutureAppointmentsException.class)
    public ResponseEntity<Map<String, Object>> handleHasFutureAppointments(HasFutureAppointmentsException ex) {
        Map<String, Object> body = new java.util.LinkedHashMap<>();
        body.put("status", 409);
        body.put("message", ex.getMessage());
        body.put("appointments", ex.getAppointments());
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(InventoryItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemNotFound(InventoryItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(InventoryItemCodeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemCodeExists(InventoryItemCodeAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(InventoryItemHasMovementsException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemHasMovements(InventoryItemHasMovementsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AnamneseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAnamneseNotFound(AnamneseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AnamneseTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAnamneseTokenNotFound(AnamneseTokenNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AnamneseTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleAnamneseTokenExpired(AnamneseTokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.GONE)
                .body(new ErrorResponse(410, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AnamneseTokenAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleAnamneseTokenAlreadyUsed(AnamneseTokenAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(LashMappingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLashMappingNotFound(LashMappingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, ex.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, message, LocalDateTime.now().toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Erro interno: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "Erro interno do servidor", LocalDateTime.now().toString()));
    }
}
