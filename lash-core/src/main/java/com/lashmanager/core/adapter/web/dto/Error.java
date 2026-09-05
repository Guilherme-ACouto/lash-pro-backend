package com.lashmanager.core.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

/**
 * Corpo de erro no padrão Pontta: {@code code}/{@code message}/{@code customCode}/{@code infoUrl},
 * com {@code param} propositalmente fora do JSON (igual ao Pontta — carrega dado interno, nunca
 * serializado).
 *
 * <p><b>Extensão do Lash</b>: {@code details} carrega dado estruturado quando o erro precisa de
 * mais que uma mensagem (ex.: lista de agendamentos futuros em
 * {@code HasFutureAppointmentsException}) — o Pontta não tem equivalente a isso; foi adicionado
 * porque {@code param} não serializa e o Lash já tinha esse caso de uso real antes da migração.
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private final String code;
    private final String message;
    private final String customCode;

    @JsonIgnore
    private final transient Object param;

    private final String infoUrl;

    private final Object details;
}
