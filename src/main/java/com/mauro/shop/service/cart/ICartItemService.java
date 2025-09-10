package com.mauro.shop.service.cart;

import com.mauro.shop.model.CartItem;

public interface ICartItemService {
	void addItemToCart(Long cartId, Long ProductId, int quantaty);
	void removeItemFromCart(Long cartId, Long ProductId);
	void updateItemQuantity(Long cartId, Long ProductId, int quantaty);
	CartItem getCartItem(Long cartId,Long productId);
}
