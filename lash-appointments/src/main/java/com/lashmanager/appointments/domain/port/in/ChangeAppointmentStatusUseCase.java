package com.lashmanager.appointments.domain.port.in;

import java.util.UUID;

public interface ChangeAppointmentStatusUseCase {
    void confirm(UUID id);
    void complete(UUID id, String paymentMethod);
    void cancel(UUID id);
    void noShow(UUID id);
}
