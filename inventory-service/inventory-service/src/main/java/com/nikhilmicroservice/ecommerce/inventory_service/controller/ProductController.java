package com.nikhilmicroservice.ecommerce.inventory_service.controller;

import com.nikhilmicroservice.ecommerce.inventory_service.dto.OrderRequestDto;
import com.nikhilmicroservice.ecommerce.inventory_service.dto.ProductDto;
import com.nikhilmicroservice.ecommerce.inventory_service.repository.ProductRepository;
import com.nikhilmicroservice.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders() {
        ServiceInstance orderServiceInstance = discoveryClient.getInstances("order-service").getFirst();
        return restClient.get()
                .uri(orderServiceInstance.getUri()+"/orders/core/helloOrders")
                .retrieve()
                .body(String.class);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> inventries = productService.getAllInventory();
        return ResponseEntity.ok(inventries);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStock(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStock(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }
}
