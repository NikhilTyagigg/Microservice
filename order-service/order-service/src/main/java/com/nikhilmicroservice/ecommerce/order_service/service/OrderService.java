package com.nikhilmicroservice.ecommerce.order_service.service;

import com.nikhilmicroservice.ecommerce.order_service.client.InventoryOpenFeignClient;
import com.nikhilmicroservice.ecommerce.order_service.dto.OrderRequestDto;
import com.nikhilmicroservice.ecommerce.order_service.entity.OrderItems;
import com.nikhilmicroservice.ecommerce.order_service.entity.OrderStatus;
import com.nikhilmicroservice.ecommerce.order_service.entity.Orders;
import com.nikhilmicroservice.ecommerce.order_service.repository.OredersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OredersRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;


    public List<OrderRequestDto> getAllOrders() {
        log.info("Order Service getAllOrders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long orderId) {
        log.info("Order Service getOrderById");
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Order Service createOrder");
        Double totalPrice = inventoryOpenFeignClient.reduceStock(orderRequestDto);
        Orders order = modelMapper.map(orderRequestDto, Orders.class);
        for(OrderItems orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
        }
        order.setPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderRequestDto.class);
    }
}
