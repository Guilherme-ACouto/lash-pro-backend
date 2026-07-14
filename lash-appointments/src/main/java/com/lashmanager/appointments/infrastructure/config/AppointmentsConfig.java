package com.lashmanager.appointments.infrastructure.config;

import com.lashmanager.appointments.application.usecase.*;
import com.lashmanager.appointments.domain.port.in.*;
import com.lashmanager.appointments.domain.port.out.AppointmentFinancialPort;
import com.lashmanager.appointments.domain.port.out.AppointmentRepository;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentsConfig {

    @Bean
    public CreateAppointmentUseCase createAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            ClientRepository clientRepository,
            ServiceRepository serviceRepository
    ) {
        return new CreateAppointmentUseCaseImpl(appointmentRepository, clientRepository, serviceRepository);
    }

    @Bean
    public UpdateAppointmentUseCase updateAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            ClientRepository clientRepository,
            ServiceRepository serviceRepository
    ) {
        return new UpdateAppointmentUseCaseImpl(appointmentRepository, clientRepository, serviceRepository);
    }

    @Bean
    public GetAppointmentUseCase getAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            ClientRepository clientRepository,
            ServiceRepository serviceRepository
    ) {
        return new GetAppointmentUseCaseImpl(appointmentRepository, clientRepository, serviceRepository);
    }

    @Bean
    public ListAppointmentsByDateUseCase listAppointmentsByDateUseCase(AppointmentRepository appointmentRepository) {
        return new ListAppointmentsByDateUseCaseImpl(appointmentRepository);
    }

    @Bean
    public ChangeAppointmentStatusUseCase changeAppointmentStatusUseCase(
            AppointmentRepository appointmentRepository,
            ClientRepository clientRepository,
            ServiceRepository serviceRepository,
            AppointmentFinancialPort financialPort
    ) {
        return new ChangeAppointmentStatusUseCaseImpl(
                appointmentRepository, clientRepository, serviceRepository, financialPort
        );
    }
}
