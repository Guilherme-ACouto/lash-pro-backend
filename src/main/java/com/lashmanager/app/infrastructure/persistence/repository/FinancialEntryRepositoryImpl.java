package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.FinancialEntry;
import com.lashmanager.app.domain.port.out.FinancialEntryRepository;
import com.lashmanager.app.infrastructure.persistence.entity.AppointmentEntity;
import com.lashmanager.app.infrastructure.persistence.entity.ClientEntity;
import com.lashmanager.app.infrastructure.persistence.entity.FinancialEntryEntity;
import com.lashmanager.app.infrastructure.persistence.mapper.FinancialEntryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FinancialEntryRepositoryImpl implements FinancialEntryRepository {

    private final FinancialEntryJpaRepository jpaRepository;
    private final FinancialEntryMapper mapper;

    @Override
    public FinancialEntry save(FinancialEntry entry) {
        FinancialEntryEntity entity = mapper.toEntity(entry);
        FinancialEntryEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<FinancialEntry> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<FinancialEntryWithCounterpart> listWithFilters(
            LocalDate from, LocalDate to,
            String category, String expenseType, String type,
            Pageable pageable) {

        Page<FinancialEntryEntity> page = jpaRepository.findWithFilters(
                from, to,
                (category != null && !category.isBlank()) ? category : null,
                (type != null && !type.isBlank()) ? type : null,
                (expenseType != null && !expenseType.isBlank()) ? expenseType : null,
                pageable);

        return page.map(entity -> {
            FinancialEntry entry = mapper.toDomain(entity);
            String counterpart = resolveCounterpart(entity);
            return new FinancialEntryWithCounterpart(entry, counterpart);
        });
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdAndAppointmentIdIsNull(UUID id) {
        return jpaRepository.existsByIdAndAppointmentIdIsNull(id);
    }

    @Override
    public java.util.List<String> findDistinctCategories() {
        return jpaRepository.findDistinctCategories();
    }

    private String resolveCounterpart(FinancialEntryEntity entity) {
        AppointmentEntity appointment = entity.getAppointment();
        if (appointment != null) {
            ClientEntity client = appointment.getClient();
            if (client != null) {
                return client.getName();
            }
        }
        return entity.getReceivedFrom();
    }
}
