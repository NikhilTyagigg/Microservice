package com.nikhilmicroservice.ecommerce.order_service.controller;

import com.nikhilmicroservice.ecommerce.order_service.dto.OrderRequestDto;
import com.nikhilmicroservice.ecommerce.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/helloOrders")
    public String helloOrders() {
        return "Hello Order Service";
    }


    @GetMapping
    public ResponseEntity<List<OrderRequestDto>>getAllOrders(HttpServletRequest request){
        log.info("Fetching all the orders");
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/id")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id){
        log.info("Fetching order with id {}", id);
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
