package com.sujal.shopingwebsitebackend1.Repo;

import com.sujal.shopingwebsitebackend1.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface orderRepo extends JpaRepository<Order,Integer> {
    Optional<Order> findByOrderId(String orderId);
}
