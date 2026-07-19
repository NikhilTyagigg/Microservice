package com.nikhilmicroservice.ecommerce.inventory_service.controller;

import com.nikhilmicroservice.ecommerce.inventory_service.dto.ProductDto;
import com.nikhilmicroservice.ecommerce.inventory_service.repository.ProductRepository;
import com.nikhilmicroservice.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

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
}
