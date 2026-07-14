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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class DeactivateClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    private DeactivateClientUseCaseImpl useCase;
    private UUID clientId;
    private Client activeClient;

    @BeforeEach
    void setUp() {
        useCase = new DeactivateClientUseCaseImpl(clientRepository, appointmentRepository);
        clientId = UUID.randomUUID();
        activeClient = Client.builder()
                .id(clientId)
                .name("Ana Lima")
                .phone("11999999999")
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void deactivate_withNoFutureAppointmentsAndForceFalse_savesWithActiveFalse() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        given(appointmentRepository.findFutureActiveByClientId(any(UUID.class), any(LocalDate.class)))
                .willReturn(Collections.emptyList());
        willAnswer(inv -> inv.getArgument(0)).given(clientRepository).save(any(Client.class));

        useCase.deactivate(clientId, false);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        assertThat(captor.getValue().isActive()).isFalse();
    }

    @Test
    void deactivate_withFutureAppointmentsAndForceFalse_throwsHasFutureAppointmentsException() {
        AppointmentSummary summary = new AppointmentSummary(
                UUID.randomUUID().toString(),
                LocalDate.now().plusDays(3),
                LocalTime.of(10, 0),
                "Extensão de cílios"
        );
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        given(appointmentRepository.findFutureActiveByClientId(any(UUID.class), any(LocalDate.class)))
                .willReturn(List.of(summary));

        assertThatThrownBy(() -> useCase.deactivate(clientId, false))
                .isInstanceOf(HasFutureAppointmentsException.class);

        then(clientRepository).should(never()).save(any());
    }

    @Test
    void deactivate_withFutureAppointmentsAndForceTrue_ignoresAppointmentsAndSaves() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        willAnswer(inv -> inv.getArgument(0)).given(clientRepository).save(any(Client.class));

        useCase.deactivate(clientId, true);

        verifyNoInteractions(appointmentRepository);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        assertThat(captor.getValue().isActive()).isFalse();
    }

    @Test
    void deactivate_withUnknownId_throwsClientNotFoundException() {
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.deactivate(clientId, false))
                .isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    void reactivate_withExistingClient_savesWithActiveTrue() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        willAnswer(inv -> inv.getArgument(0)).given(clientRepository).save(any(Client.class));

        useCase.reactivate(clientId);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        assertThat(captor.getValue().isActive()).isTrue();
    }

    @Test
    void reactivate_withUnknownId_throwsClientNotFoundException() {
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.reactivate(clientId))
                .isInstanceOf(ClientNotFoundException.class);
    }
}
