package com.lashmanager.fichas.domain.port.out;

import com.lashmanager.fichas.domain.model.Ficha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FichaRepository {
    Optional<Ficha> findById(UUID id);
    Optional<Ficha> findByClientId(UUID clientId);
    boolean existsByClientId(UUID clientId);
    Ficha save(Ficha ficha);
    Page<Ficha> listWithFilters(String search, Pageable pageable);
}
