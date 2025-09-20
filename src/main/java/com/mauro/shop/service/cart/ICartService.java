package com.mauro.shop.service.cart;

import java.math.BigDecimal;

import com.mauro.shop.model.Cart;

public interface ICartService {
	Cart getCart(Long id);
	Cart getCartByUserId(Long userId);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Long initializeNewCart();
}
