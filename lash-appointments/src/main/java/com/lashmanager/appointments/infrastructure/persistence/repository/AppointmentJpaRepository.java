package com.lashmanager.appointments.infrastructure.persistence.repository;

import com.lashmanager.appointments.infrastructure.persistence.entity.AppointmentEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, UUID> {

    @Query(
            "SELECT a FROM AppointmentEntity a WHERE a.scheduledDate = :date AND a.status != 'CANCELLED' ORDER BY a.scheduledTime")
    List<AppointmentEntity> findActiveByDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM AppointmentEntity a WHERE a.scheduledDate = :date ORDER BY a.scheduledTime")
    List<AppointmentEntity> findByDate(@Param("date") LocalDate date);

    @Query(
            "SELECT a FROM AppointmentEntity a WHERE a.scheduledDate >= :startDate AND a.scheduledDate <= :endDate ORDER BY a.scheduledDate, a.scheduledTime")
    List<AppointmentEntity> findByDateRange(
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
            "SELECT a FROM AppointmentEntity a WHERE a.clientId = :clientId AND a.scheduledDate > :from AND a.status IN ('SCHEDULED','CONFIRMED') ORDER BY a.scheduledDate, a.scheduledTime")
    List<AppointmentEntity> findFutureActiveByClientId(@Param("clientId") UUID clientId, @Param("from") LocalDate from);

    @Query(
            "SELECT COUNT(a) FROM AppointmentEntity a WHERE a.serviceId = :serviceId AND a.scheduledDate >= :from AND a.status IN ('SCHEDULED','CONFIRMED')")
    long countActiveByServiceId(@Param("serviceId") UUID serviceId, @Param("from") LocalDate from);

    @Modifying
    @Transactional
    @Query("DELETE FROM AppointmentEntity a WHERE a.clientId = :clientId AND a.scheduledDate >= :from")
    void deleteFutureByClientId(@Param("clientId") UUID clientId, @Param("from") LocalDate from);

    @Modifying
    @Transactional
    @Query("UPDATE AppointmentEntity a SET a.clientId = null WHERE a.clientId = :clientId AND a.scheduledDate < :from")
    void unlinkClientFromPastAppointments(@Param("clientId") UUID clientId, @Param("from") LocalDate from);
}
