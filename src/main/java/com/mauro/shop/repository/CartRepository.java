package com.mauro.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mauro.shop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
