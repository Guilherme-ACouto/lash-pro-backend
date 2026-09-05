package com.lashmanager.appointments.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Projeção de leitura cross-agregado — junta {@code Appointment} com dado de {@code Client}/
 * {@code ServiceOffering} (nome, preço). Não é o agregado {@code Appointment} em si (que não
 * carrega nome de cliente/serviço) — mora do lado de {@code AppointmentQueryService}, nunca do
 * lado de escrita, igual ao padrão real do Pontta pra enriquecimento cross-agregado (ex.:
 * {@code OccurrenceDescription}, que também não é a entidade {@code Occurrence}).
 */
public record AppointmentDetails(
        UUID id,
        UUID clientId,
        String clientName,
        UUID serviceId,
        String serviceName,
        BigDecimal servicePrice,
        String scheduledDate,
        String scheduledTime,
        int durationMinutes,
        String status,
        String notes,
        String financialEntryId,
        String createdAt) {}
