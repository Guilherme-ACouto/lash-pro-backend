package com.lashmanager.clients.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.lashmanager.clients.domain.exception.ClientNotFoundException;
import com.lashmanager.clients.domain.exception.HasFutureAppointmentsException;
import com.lashmanager.clients.domain.model.AppointmentSummary;
import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientAppointmentPort;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Desativar e reativar cliente")
class DeactivateClientUseCaseImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientAppointmentPort clientAppointmentPort;

    private DeactivateClientUseCaseImpl useCase;
    private UUID clientId;
    private Client activeClient;

    @BeforeEach
    void setUp() {
        useCase = new DeactivateClientUseCaseImpl(clientRepository, clientAppointmentPort);
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
    @DisplayName("deve salvar cliente com active=false quando não há agendamentos futuros e force=false")
    void deactivate_withNoFutureAppointmentsAndForceFalse_savesWithActiveFalse() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        given(clientAppointmentPort.findFutureActiveByClientId(any(UUID.class), any(LocalDate.class)))
                .willReturn(Collections.emptyList());
        willAnswer(inv -> inv.getArgument(0)).given(clientRepository).save(any(Client.class));

        useCase.deactivate(clientId, false);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        assertThat(captor.getValue().isActive()).isFalse();
    }

    @Test
    @DisplayName("deve lançar HasFutureAppointmentsException quando há agendamentos futuros e force=false")
    void deactivate_withFutureAppointmentsAndForceFalse_throwsHasFutureAppointmentsException() {
        AppointmentSummary summary = new AppointmentSummary(
                UUID.randomUUID().toString(), LocalDate.now().plusDays(3), LocalTime.of(10, 0), "Extensão de cílios");
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        given(clientAppointmentPort.findFutureActiveByClientId(any(UUID.class), any(LocalDate.class)))
                .willReturn(List.of(summary));

        assertThatThrownBy(() -> useCase.deactivate(clientId, false))
                .isInstanceOf(HasFutureAppointmentsException.class);

        then(clientRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("deve cancelar agendamentos futuros e desativar o cliente quando force=true")
    void deactivate_withFutureAppointmentsAndForceTrue_ignoresAppointmentsAndSaves() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        willAnswer(inv -> inv.getArgument(0)).given(clientRepository).save(any(Client.class));

        useCase.deactivate(clientId, true);

        then(clientAppointmentPort).should(never()).findFutureActiveByClientId(any(), any());
        then(clientAppointmentPort).should().deleteFutureAppointmentsByClientId(any(), any());

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        assertThat(captor.getValue().isActive()).isFalse();
    }

    @Test
    @DisplayName("deve lançar ClientNotFoundException ao desativar quando o id não existe")
    void deactivate_withUnknownId_throwsClientNotFoundException() {
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.deactivate(clientId, false)).isInstanceOf(ClientNotFoundException.class);
    }

    @Test
    @DisplayName("deve salvar cliente com active=true ao reativar")
    void reactivate_withExistingClient_savesWithActiveTrue() {
        given(clientRepository.findById(clientId)).willReturn(Optional.of(activeClient));
        willAnswer(inv -> inv.getArgument(0)).given(clientRepository).save(any(Client.class));

        useCase.reactivate(clientId);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());
        assertThat(captor.getValue().isActive()).isTrue();
    }

    @Test
    @DisplayName("deve lançar ClientNotFoundException ao reativar quando o id não existe")
    void reactivate_withUnknownId_throwsClientNotFoundException() {
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.reactivate(clientId)).isInstanceOf(ClientNotFoundException.class);
    }
}
