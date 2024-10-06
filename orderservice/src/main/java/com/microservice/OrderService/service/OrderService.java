package com.microservice.OrderService.service;

import com.microservice.OrderService.client.InventoryClient;
//import com.microservice.OrderService.dto.OrderLineItemsDto;
import com.microservice.OrderService.dto.OrderRequest;
//import com.microservice.OrderService.event.OrderPlacedEvent;
import com.microservice.OrderService.event.OrderPlacedEvent;
import com.microservice.OrderService.model.Inventory;
import com.microservice.OrderService.model.Order;
//import com.microservice.OrderService.model.OrderLineItems;
import com.microservice.OrderService.repository.OrderRepository;
//import javassist.bytecode.stackmap.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.reactive.function.client.WebClient;

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
//    @Autowired
//    private WebClient.Builder webClientBuilder;
//    private boolean allProductInStock;
//    private final Tracer tracer = null;
//OrderPlacedEvent
    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
//private final ApplicationEventPublisher applicationEventPublisher;


    //    public OrderService(OrderRepository orderRepository, WebClient webClient, boolean allProductInStock) {
//        this.orderRepository = orderRepository;
//        this.webClient = webClient;
//        this.allProductInStock = allProductInStock;
//    }
//throws IllegalAccessException
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
//
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
//            applicationEventPublisher.publishEvent(new OrderPlacedEvent(order.getOrderNumber()));
            return "Order is placed successfully";

        } else {
            throw new RuntimeException("The given skuCode "+orderRequest.getSkuCode()+"is not available in inventory.");
        }

//       List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
//                                                            .stream()
//                                                            .map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();
//
//       order.setOrderLineItemsList(orderLineItems);
//
//       List<String> skuCodes = orderLineItems.stream()
//                                             .map(OrderLineItems::getSkuCode)
//                                             .toList();
//
////       Call inventoryService and if the product is there then place the order
//        Inventory[] inventoryResponseArray = webClientBuilder.build().get()
//                 .uri("http://inventory-service/api/inventory",
//                         uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                 .retrieve()
//                 .bodyToMono(Inventory[].class)
//                 .block();
        //If all products are in the stock then we are going to save the order
        //In allMatch method if we get even one false from getIsInStock then it will return false
//        boolean allProductInStock = Arrays.stream(inventoryResponseArray).allMatch(Inventory::getIsInStock);
//
//        if(allProductInStock){
//            orderRepository.save(order);
//            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
//            return "Order Placed Successfully";
//        }
//        else {
//            throw new IllegalAccessException("Product is not in stock, please try again later");
//        }

    }

//    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
//        OrderLineItems orderLineItems = new OrderLineItems();
//        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
//        orderLineItems.setPrice(orderLineItemsDto.getPrice());
//        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
//        return orderLineItems;
//    }
}
