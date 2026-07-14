package com.lashmanager.finance.infrastructure.persistence.repository;

import com.lashmanager.appointments.infrastructure.persistence.repository.AppointmentJpaRepository;
import com.lashmanager.clients.infrastructure.persistence.repository.ClientJpaRepository;
import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.finance.infrastructure.persistence.entity.FinancialEntryEntity;
import com.lashmanager.finance.infrastructure.persistence.mapper.FinancialEntryPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FinancialEntryRepositoryImpl implements FinancialEntryRepository {

    private final FinancialEntryJpaRepository jpaRepository;
    private final FinancialEntryPersistenceMapper mapper;
    private final AppointmentJpaRepository appointmentJpaRepository;
    private final ClientJpaRepository clientJpaRepository;

    @Override
    public FinancialEntry save(FinancialEntry entry) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(entry)));
    }

    @Override
    public Optional<FinancialEntry> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<FinancialEntryWithCounterpart> listWithFilters(
            LocalDate from, LocalDate to,
            String category, String expenseType, String type,
            Pageable pageable
    ) {
        return jpaRepository.findWithFilters(
                from, to,
                blankToNull(category), blankToNull(type), blankToNull(expenseType),
                pageable
        ).map(entity -> new FinancialEntryWithCounterpart(
                mapper.toDomain(entity),
                resolveCounterpart(entity)
        ));
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
    public List<String> findDistinctCategories() {
        return jpaRepository.findDistinctCategories();
    }

    private String resolveCounterpart(FinancialEntryEntity entity) {
        if (entity.getAppointmentId() != null) {
            return appointmentJpaRepository.findById(entity.getAppointmentId())
                    .flatMap(a -> a.getClientId() != null
                            ? clientJpaRepository.findById(a.getClientId()).map(c -> c.getName())
                            : Optional.empty())
                    .orElse(null);
        }
        return entity.getReceivedFrom();
    }

    private static String blankToNull(String s) {
        return (s != null && !s.isBlank()) ? s : null;
    }
}
