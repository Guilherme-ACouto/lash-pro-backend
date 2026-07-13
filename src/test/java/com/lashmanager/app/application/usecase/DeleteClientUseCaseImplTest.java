package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.ClientNotFoundException;
import com.lashmanager.app.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.app.domain.model.AppointmentSummary;
import com.lashmanager.app.domain.model.Client;
import com.lashmanager.app.domain.port.out.AppointmentRepository;
import com.lashmanager.app.domain.port.out.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class DeleteClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    private DeleteClientUseCaseImpl useCase;
    private UUID clientId;
    private Client existingClient;

    @BeforeEach
    void setUp() {
        useCase = new DeleteClientUseCaseImpl(clientRepository, appointmentRepository);
        clientId = UUID.randomUUID();
        existingClient = Client.builder()
                .id(clientId)
                .name("Ana Lima")
                .phone("11999999999")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void execute_withNoFutureAppointments_deletesInCorrectOrder() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));
        given(appointmentRepository.findFutureActiveByClientId(eq(clientId), any()))
                .willReturn(Collections.emptyList());

        useCase.execute(clientId);

        InOrder inOrder = inOrder(appointmentRepository, clientRepository);
        inOrder.verify(appointmentRepository).deleteFutureAppointmentsByClientId(eq(clientId), any());
        inOrder.verify(appointmentRepository).unlinkClientFromPastAppointments(eq(clientId), any());
        inOrder.verify(clientRepository).deleteById(clientId);
    }

    @Test
    void execute_withUnknownId_throwsClientNotFoundException() {
        given(clientRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(clientId))
                .isInstanceOf(ClientNotFoundException.class);

        then(appointmentRepository).shouldHaveNoInteractions();
    }

    @Test
    void execute_withFutureActiveAppointments_throwsHasFutureAppointmentsException() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(existingClient));

        AppointmentSummary summary1 = new AppointmentSummary(
                UUID.randomUUID().toString(),
                LocalDate.now().plusDays(3),
                LocalTime.of(10, 0),
                "Extensão de Cílios"
        );
        AppointmentSummary summary2 = new AppointmentSummary(
                UUID.randomUUID().toString(),
                LocalDate.now().plusDays(7),
                LocalTime.of(14, 30),
                "Manutenção"
        );

        given(appointmentRepository.findFutureActiveByClientId(eq(clientId), any()))
                .willReturn(List.of(summary1, summary2));

        assertThatThrownBy(() -> useCase.execute(clientId))
                .isInstanceOf(HasFutureAppointmentsException.class);

        then(clientRepository).should(never()).deleteById(any());
    }
}
