package com.lashmanager.dashboard.domain.model;

/** {@code date} em ISO ("YYYY-MM-DD") — o frontend faz {@code date.split('-')} direto. */
public record AppointmentDayStat(String date, long completed, long confirmed, long scheduled, long cancelled) {}
