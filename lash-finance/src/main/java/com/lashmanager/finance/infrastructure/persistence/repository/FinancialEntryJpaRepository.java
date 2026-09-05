package com.lashmanager.finance.infrastructure.persistence.repository;

import com.lashmanager.finance.infrastructure.persistence.entity.FinancialEntryEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinancialEntryJpaRepository extends JpaRepository<FinancialEntryEntity, UUID> {

    @Query("""
            SELECT f FROM FinancialEntryEntity f
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
    List<String> findDistinctCategories();
}
