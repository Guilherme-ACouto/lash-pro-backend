package com.lashmanager.dashboard.infrastructure.config;

import com.lashmanager.appointments.infrastructure.persistence.repository.AppointmentJpaRepository;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.dashboard.application.usecase.GetDashboardSummaryUseCaseImpl;
import com.lashmanager.dashboard.application.usecase.GetTodayScheduleUseCaseImpl;
import com.lashmanager.finance.infrastructure.persistence.repository.FinancialEntryJpaRepository;
import com.lashmanager.services.infrastructure.persistence.repository.ServiceJpaRepository;
import com.lashmanager.stock.infrastructure.persistence.repository.InventoryItemJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DashboardConfig {

    @Bean
    public GetDashboardSummaryUseCaseImpl getDashboardSummaryUseCase(
            ClientJpaRepository clientJpaRepository,
            AppointmentJpaRepository appointmentJpaRepository,
            FinancialEntryJpaRepository financialEntryJpaRepository,
            InventoryItemJpaRepository inventoryItemJpaRepository) {
        return new GetDashboardSummaryUseCaseImpl(
                clientJpaRepository,
                appointmentJpaRepository,
                financialEntryJpaRepository,
                inventoryItemJpaRepository
        );
    }

    @Bean
    public GetTodayScheduleUseCaseImpl getTodayScheduleUseCase(
            AppointmentJpaRepository appointmentJpaRepository,
            ClientJpaRepository clientJpaRepository,
            ServiceJpaRepository serviceJpaRepository) {
        return new GetTodayScheduleUseCaseImpl(
                appointmentJpaRepository,
                clientJpaRepository,
                serviceJpaRepository
        );
    }
}
