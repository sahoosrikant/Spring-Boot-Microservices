package com.microservice.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
//    private Long id;
//    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
