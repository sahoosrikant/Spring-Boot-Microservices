package com.microservice.OrderService.controller;

import com.microservice.OrderService.dto.OrderRequest;
import com.microservice.OrderService.service.OrderService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.retry.annotation.Retry;
//import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="inventory")
    @Retry(name="inventory")
    public CompletableFuture<String> placeOrder (@RequestBody OrderRequest orderRequest){
        log.info("We are in the controller layer");
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }




//    @ResponseStatus(HttpStatus.CREATED)
    // the name="inventory" is same as name provided in the application.properties
//    @CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name ="inventory")
//    @Retry(name ="inventory")
//    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
////        return CompletableFuture.supplyAsync(()-> {
////            try {
//                return orderService.placeOrder(orderRequest);
////            } catch (IllegalAccessException e) {
////                throw new RuntimeException(e);
////            }
//        });
//    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong, please order after some time!");
    }

}
