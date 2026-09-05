package com.lashmanager.clients.infrastructure.persistence.repository;

import com.lashmanager.clients.domain.model.Client;
import com.lashmanager.clients.domain.port.out.ClientRepository;
import com.lashmanager.clients.infrastructure.persistence.mapper.ClientMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

  private final ClientJpaRepository jpaRepository;
  private final ClientMapper mapper;

  @Override
  public Client save(Client client) {
    return mapper.toDomain(jpaRepository.save(mapper.toEntity(client)));
  }

  @Override
  public Optional<Client> findById(UUID id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public boolean existsByPhone(String phone) {
    return jpaRepository.existsByPhone(phone);
  }

  @Override
  public boolean existsByPhoneAndIdNot(String phone, UUID id) {
    return jpaRepository.existsByPhoneAndIdNot(phone, id);
  }

  @Override
  public void deleteById(UUID id) {
    jpaRepository.deleteById(id);
  }
}
