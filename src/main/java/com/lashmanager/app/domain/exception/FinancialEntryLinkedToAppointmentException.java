package com.lashmanager.app.domain.exception;

public class FinancialEntryLinkedToAppointmentException extends RuntimeException {
    public FinancialEntryLinkedToAppointmentException() {
        super("Não é possível excluir lançamento vinculado a agendamento");
    }
}
