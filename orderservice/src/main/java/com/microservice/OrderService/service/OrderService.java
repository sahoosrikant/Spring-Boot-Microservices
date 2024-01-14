package com.microservice.OrderService.service;

import com.microservice.OrderService.dto.OrderLineItemsDto;
import com.microservice.OrderService.dto.OrderRequest;
import com.microservice.OrderService.model.InventoryResponse;
import com.microservice.OrderService.model.Order;
import com.microservice.OrderService.model.OrderLineItems;
import com.microservice.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
//@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    private boolean allProductInStock;

//    public OrderService(OrderRepository orderRepository, WebClient webClient, boolean allProductInStock) {
//        this.orderRepository = orderRepository;
//        this.webClient = webClient;
//        this.allProductInStock = allProductInStock;
//    }

    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                                                            .stream()
                                                            .map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();

       order.setOrderLineItemsList(orderLineItems);

       List<String> skuCodes = orderLineItems.stream()
                                             .map(OrderLineItems::getSkuCode)
                                             .toList();

//       Call inventoryService and if the product is there then place the order
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                 .uri("http://inventory-service/api/inventory",
                         uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                 .retrieve()
                 .bodyToMono(InventoryResponse[].class)
                 .block();
        //If all products are in the stock then we are going to save the order
        //In allMatch method if we get even one false from getIsInStock then it will return false
        boolean allProductInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);

        if(allProductInStock){
            orderRepository.save(order);
        }
        else {
            throw new IllegalAccessException("Product is not in stock, please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
