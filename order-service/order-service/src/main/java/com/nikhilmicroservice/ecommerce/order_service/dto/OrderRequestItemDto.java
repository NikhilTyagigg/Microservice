package com.nikhilmicroservice.ecommerce.order_service.dto;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long Id;
    private Long productId;
    private Integer quantity;
}
