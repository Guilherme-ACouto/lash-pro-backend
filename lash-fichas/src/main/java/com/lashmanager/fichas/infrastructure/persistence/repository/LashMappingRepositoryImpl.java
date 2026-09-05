package com.lashmanager.fichas.infrastructure.persistence.repository;

import com.lashmanager.fichas.domain.model.LashMapping;
import com.lashmanager.fichas.domain.port.out.LashMappingRepository;
import com.lashmanager.fichas.infrastructure.persistence.mapper.LashMappingMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LashMappingRepositoryImpl implements LashMappingRepository {

  private final LashMappingJpaRepository jpaRepository;
  private final LashMappingMapper mapper;

  @Override
  public Optional<LashMapping> findById(UUID id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public LashMapping save(LashMapping mapping) {
    return mapper.toDomain(jpaRepository.save(mapper.toEntity(mapping)));
  }

  @Override
  public void delete(UUID id) {
    jpaRepository.deleteById(id);
  }
}
