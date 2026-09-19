package com.lashmanager.dashboard.domain.port.in;

import com.lashmanager.dashboard.domain.model.DashboardData;
import com.lashmanager.dashboard.domain.model.DashboardPeriod;

/** Módulo 100% leitura — sem UseCase de escrita, sem Resource de comando. */
public interface DashboardQueryService {

    DashboardData getDashboard(DashboardPeriod period);
}
