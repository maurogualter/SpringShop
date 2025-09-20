package com.mauro.shop.service.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Cart;
import com.mauro.shop.repository.CartItemRepository;
import com.mauro.shop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final AtomicLong cartIdGenerator = new AtomicLong(0);
	
	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
	}

	@Transactional
	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);
		
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		cart.uptadeTotalAmount();
		return cart.getTotalAmount();
	}
	
	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		//Long NewCartId = cartIdGenerator.incrementAndGet();
		//newCart.setId(NewCartId);
		return cartRepository.save(newCart).getId();
	}

	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
	}

}
