package com.lashmanager.stock.application.query;

import com.lashmanager.stock.domain.exception.InventoryItemNotFoundException;
import com.lashmanager.stock.domain.model.InventoryItem;
import com.lashmanager.stock.domain.port.in.InventoryItemQueryService;
import com.lashmanager.stock.domain.port.out.InventoryItemQueryRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryItemQueryServiceImpl implements InventoryItemQueryService {

    private final InventoryItemQueryRepository itemQueryRepository;

    @Override
    public InventoryItem getById(UUID id) {
        return itemQueryRepository.findById(id).orElseThrow(() -> new InventoryItemNotFoundException(id));
    }

    @Override
    public Page<InventoryItem> list(String search, String status, String filter, Pageable pageable) {
        Boolean active = resolveActive(status);
        boolean onlyLowStock = "LOW_STOCK".equalsIgnoreCase(filter);
        String normalizedSearch = search != null ? search : "";
        return itemQueryRepository.listWithFilters(normalizedSearch, active, onlyLowStock, pageable);
    }

    private Boolean resolveActive(String status) {
        if ("ACTIVE".equalsIgnoreCase(status)) {
            return true;
        }
        return "INACTIVE".equalsIgnoreCase(status) ? false : null;
    }
}
