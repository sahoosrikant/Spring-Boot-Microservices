package com.microservice.OrderService.service;

import com.microservice.OrderService.client.InventoryClient;
import com.microservice.OrderService.dto.OrderRequest;
import com.microservice.OrderService.event.OrderPlacedEvent;
import com.microservice.OrderService.model.Inventory;
import com.microservice.OrderService.model.Order;
import com.microservice.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;
    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest)  {
        log.info("We are in the service layer");


        var isProductInStock = inventoryClient.getInventoryOfOrder(orderRequest.getSkuCode(),
                                        orderRequest.getQuantity());

        if(isProductInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.getSkuCode());
            order.setQuantity(orderRequest.getQuantity());
            order.setPrice(orderRequest.getPrice());
            orderRepository.save(order);
            log.info("Before send from order service {}",order.getOrderNumber());
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order is placed successfully";

        } else {
            throw new RuntimeException("The given skuCode "+orderRequest.getSkuCode()+"is not available in inventory.");
        }
    }
}
