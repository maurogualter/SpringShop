package com.mauro.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mauro.shop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	void deleteAllByCartId(Long id);

}
