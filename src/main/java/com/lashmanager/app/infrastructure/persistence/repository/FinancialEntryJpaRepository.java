package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.infrastructure.persistence.entity.FinancialEntryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface FinancialEntryJpaRepository extends JpaRepository<FinancialEntryEntity, UUID> {

    @Query("""
            SELECT f FROM FinancialEntryEntity f
            LEFT JOIN FETCH f.appointment a
            LEFT JOIN FETCH a.client c
            WHERE (:type IS NULL OR f.type = :type)
            AND (:expenseType IS NULL OR f.expenseType = :expenseType)
            AND f.dueDate >= :from AND f.dueDate <= :to
            AND (:category IS NULL OR f.category = :category)
            ORDER BY f.dueDate DESC
            """)
    Page<FinancialEntryEntity> findWithFilters(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("category") String category,
            @Param("type") String type,
            @Param("expenseType") String expenseType,
            Pageable pageable);

    boolean existsByIdAndAppointmentIdIsNull(UUID id);

    @Query("SELECT DISTINCT f.category FROM FinancialEntryEntity f WHERE f.category IS NOT NULL ORDER BY f.category")
    java.util.List<String> findDistinctCategories();
}
