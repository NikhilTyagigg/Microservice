package com.nikhilmicroservice.ecommerce.inventory_service.service;

import com.nikhilmicroservice.ecommerce.inventory_service.dto.OrderRequestDto;
import com.nikhilmicroservice.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.nikhilmicroservice.ecommerce.inventory_service.dto.ProductDto;
import com.nikhilmicroservice.ecommerce.inventory_service.entity.Product;
import com.nikhilmicroservice.ecommerce.inventory_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private  final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory(){
        log.info("Getting all inventory");
        List<Product> inventories= productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
    }
    public ProductDto getProductById(Long id){
        log.info("Getting product by id: {}",id);
        Optional<Product> inventory=productRepository.findById(id);
     return inventory.map(item->modelMapper.map(item,ProductDto.class))
             .orElseThrow(()->new RuntimeException("Inventory not found"));
    }

    @Transactional
    public Double reduceStock(OrderRequestDto orderRequestDto) {
        log.info("Reducing stock for inventory");
        Double totalPrice=0.0;
        for(OrderRequestItemDto orderRequestItemDto:orderRequestDto.getItems())
        {
            Long productId=orderRequestItemDto.getProductId();
            Integer quantity=orderRequestItemDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
            if(product.getStock()<quantity)
            {
                throw new RuntimeException("Insufficient stock for product: " + productId);
            }
            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }
}
