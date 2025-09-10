package com.mauro.shop.service.cart;

import org.springframework.stereotype.Service;

import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Cart;
import com.mauro.shop.model.CartItem;
import com.mauro.shop.model.Product;
import com.mauro.shop.repository.CartItemRepository;
import com.mauro.shop.repository.CartRepository;
import com.mauro.shop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final IProductService productService;
	private final ICartService cartService;
	
	@Override
	public void addItemToCart(Long cartId, Long productId, int quantaty) {
		//1. get the cart
		//2. get the products
		//3. check if the product already in the car
		//4. than if them increase the quantity with requested quantity
		//5. if no, initiate a new cartItem entry
		
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		CartItem cartItem = null;
		try {
			cartItem = getCartItem(cartId, productId);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			cartItem = new CartItem();
		}
		
//		CartItem cartItem = cart.getItems()
//				.stream()
//				.filter(item-> item.getProduct().getId().equals(productId))
//				.findFirst()
//				.orElse(new CartItem());
		
		if(cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantaty(quantaty);
			cartItem.setUnitPrice(product.getPrice());
			
		}else {
			cartItem.setQuantaty(cartItem.getQuantaty()+quantaty);
			
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		CartItem cartItemToRemove = getCartItem(cartId, productId);
		cart.removeItem(cartItemToRemove);
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long ProductId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		cart.getItems().stream()
		.filter(item-> item.getProduct().getId().equals(ProductId))
		.findFirst()
		.ifPresent(item ->{
			item.setQuantaty(quantity);
			item.setUnitPrice(item.getProduct().getPrice());
			item.setTotalPrice();
		});
		cart.uptadeTotalAmount();
		cartRepository.save(cart);
	}
	
	@Override
	public CartItem getCartItem(Long cartId,Long productId) {
		Cart cart = cartService.getCart(cartId);
		return  cart.getItems().stream()
				.filter(item-> item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(()-> new ResourceNotFoundException("Product not found"));
	}
	
}
