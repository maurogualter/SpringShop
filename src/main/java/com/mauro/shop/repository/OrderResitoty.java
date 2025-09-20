package com.mauro.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mauro.shop.model.Order;

public interface OrderResitoty extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);
}
