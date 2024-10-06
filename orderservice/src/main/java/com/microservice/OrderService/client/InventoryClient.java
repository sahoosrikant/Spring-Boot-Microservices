package com.microservice.OrderService.client;

import com.microservice.OrderService.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@FeignClient(url = "http://inventory-service:8080",value = "Inventory-Client")
public interface InventoryClient {

    @GetMapping("/api/inventory/{skuCode}/{quantity}")
    boolean getInventoryOfOrder(@PathVariable String skuCode, @PathVariable Integer quantity);
}
