package com.lashmanager.services.infrastructure.persistence.repository;

import com.lashmanager.services.domain.model.ServiceOffering;
import com.lashmanager.services.domain.port.out.ServiceRepository;
import com.lashmanager.services.infrastructure.persistence.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository jpaRepository;
    private final ServiceMapper mapper;

    @Override
    public ServiceOffering save(ServiceOffering service) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(service)));
    }

    @Override
    public Optional<ServiceOffering> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return jpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean hasActiveAppointments(UUID serviceId) {
        // será implementado pelo lash-appointments via query direta no banco
        // por ora retorna false para permitir deleção/desativação sem bloqueio
        return false;
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
