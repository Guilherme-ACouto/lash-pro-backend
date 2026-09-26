package com.bravapro.dashboard.domain.port.in;

import com.bravapro.dashboard.domain.model.DashboardData;
import com.bravapro.dashboard.domain.model.DashboardPeriod;

/** Módulo 100% leitura — sem UseCase de escrita, sem Resource de comando. */
public interface DashboardQueryService {

    DashboardData getDashboard(DashboardPeriod period);
}
