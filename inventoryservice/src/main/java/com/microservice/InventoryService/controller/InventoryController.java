package com.microservice.InventoryService.controller;

import com.microservice.InventoryService.dto.InventoryResponse;
import com.microservice.InventoryService.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{skuCode}/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode, @PathVariable Integer quantity) throws InterruptedException {
//        try {
            log.info("Received inventory check request for skuCode: {}", skuCode);
            return inventoryService.isInStock(skuCode,quantity);
//        }
////        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
