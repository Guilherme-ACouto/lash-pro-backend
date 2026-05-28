package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, UUID> {

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.service WHERE a.id = :id")
    Optional<AppointmentEntity> findByIdWithDetails(@Param("id") UUID id);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.service WHERE a.scheduledDate = :date ORDER BY a.scheduledTime")
    List<AppointmentEntity> findByDateWithDetails(@Param("date") LocalDate date);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.service WHERE a.scheduledDate = :date AND a.status != 'CANCELLED'")
    List<AppointmentEntity> findActiveByDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.service WHERE a.scheduledDate >= :startDate AND a.scheduledDate <= :endDate ORDER BY a.scheduledDate, a.scheduledTime")
    List<AppointmentEntity> findByDateRangeWithDetails(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.client.id = :clientId AND a.scheduledDate > :today AND a.status IN ('SCHEDULED','CONFIRMED')")
    long countFutureActiveByClientId(@Param("clientId") UUID clientId, @Param("today") LocalDate today);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.service.id = :serviceId AND a.scheduledDate > :today AND a.status IN ('SCHEDULED','CONFIRMED')")
    long countFutureActiveByServiceId(@Param("serviceId") UUID serviceId, @Param("today") LocalDate today);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.client.id = :clientId")
    long countAllByClientId(@Param("clientId") UUID clientId);

    @Query("SELECT COUNT(a) FROM AppointmentEntity a WHERE a.service.id = :serviceId")
    long countAllByServiceId(@Param("serviceId") UUID serviceId);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.service WHERE a.client.id = :clientId AND a.scheduledDate > :today AND a.status IN ('SCHEDULED','CONFIRMED') ORDER BY a.scheduledDate, a.scheduledTime")
    List<AppointmentEntity> findFutureActiveByClientId(@Param("clientId") UUID clientId, @Param("today") LocalDate today);

    @Query("SELECT a FROM AppointmentEntity a LEFT JOIN FETCH a.client LEFT JOIN FETCH a.service WHERE a.service.id = :serviceId AND a.scheduledDate > :today AND a.status IN ('SCHEDULED','CONFIRMED') ORDER BY a.scheduledDate, a.scheduledTime")
    List<AppointmentEntity> findFutureActiveByServiceId(@Param("serviceId") UUID serviceId, @Param("today") LocalDate today);

    @Modifying
    @Transactional
    @Query("UPDATE AppointmentEntity a SET a.client = null WHERE a.client.id = :clientId AND a.scheduledDate < :today")
    void unlinkClientFromPastAppointments(@Param("clientId") UUID clientId, @Param("today") LocalDate today);

    @Modifying
    @Transactional
    @Query("DELETE FROM AppointmentEntity a WHERE a.client.id = :clientId AND a.scheduledDate >= :today")
    void deleteFutureByClientId(@Param("clientId") UUID clientId, @Param("today") LocalDate today);
}
