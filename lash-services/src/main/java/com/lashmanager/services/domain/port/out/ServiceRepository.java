package com.lashmanager.services.domain.port.out;

import com.lashmanager.services.domain.model.ServiceOffering;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem/busca) foi separada para
 * ServiceQueryRepository (RBK-27) — findById continua aqui porque os use
 * cases de escrita (Update/Deactivate/Delete) precisam do agregado completo.
 */
public interface ServiceRepository {
    ServiceOffering save(ServiceOffering service);
    Optional<ServiceOffering> findById(UUID id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean hasActiveAppointments(UUID serviceId);
    void deleteById(UUID id);
}
