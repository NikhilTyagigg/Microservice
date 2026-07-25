package com.nikhilmicroservice.ecommerce.inventory_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-service", path = "/orders")
public interface OrdersFeignClient {

}
