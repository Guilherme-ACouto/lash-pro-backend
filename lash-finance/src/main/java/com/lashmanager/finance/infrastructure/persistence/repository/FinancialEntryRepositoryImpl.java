package com.lashmanager.finance.infrastructure.persistence.repository;

import com.lashmanager.finance.domain.model.FinancialEntry;
import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.finance.infrastructure.persistence.mapper.FinancialEntryPersistenceMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FinancialEntryRepositoryImpl implements FinancialEntryRepository {

  private final FinancialEntryJpaRepository jpaRepository;
  private final FinancialEntryPersistenceMapper mapper;

  @Override
  public FinancialEntry save(FinancialEntry entry) {
    return mapper.toDomain(jpaRepository.save(mapper.toEntity(entry)));
  }

  @Override
  public Optional<FinancialEntry> findById(UUID id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public void delete(UUID id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsByIdAndAppointmentIdIsNull(UUID id) {
    return jpaRepository.existsByIdAndAppointmentIdIsNull(id);
  }
}
