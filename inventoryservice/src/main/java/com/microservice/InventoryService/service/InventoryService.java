package com.microservice.InventoryService.service;

import com.microservice.InventoryService.dto.InventoryResponse;
import com.microservice.InventoryService.model.Inventory;
import com.microservice.InventoryService.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode){
       return inventoryRepository.findBySkuCodeIn(skuCode).stream()
               .map(inventory ->
                   InventoryResponse.builder()
                           .skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getQuantity() > 0)
                           .build()
               ).toList();
    }
}
