package com.microservice.InventoryService.service;

import com.microservice.InventoryService.dto.InventoryResponse;
import com.microservice.InventoryService.model.Inventory;
import com.microservice.InventoryService.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public boolean isInStock(String skuCode, Integer quantity) throws InterruptedException {
       return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode,quantity);
    }
}
