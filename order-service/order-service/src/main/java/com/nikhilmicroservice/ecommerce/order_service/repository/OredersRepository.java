package com.nikhilmicroservice.ecommerce.order_service.repository;

import com.nikhilmicroservice.ecommerce.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OredersRepository extends JpaRepository<Orders,Long> {

}
