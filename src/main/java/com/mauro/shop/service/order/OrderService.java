package com.mauro.shop.service.order;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mauro.shop.dto.OrderDto;
import com.mauro.shop.enums.OrderStatus;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Cart;
import com.mauro.shop.model.Order;
import com.mauro.shop.model.OrderItem;
import com.mauro.shop.model.Product;
import com.mauro.shop.repository.OrderResitoty;
import com.mauro.shop.repository.ProductRepository;
import com.mauro.shop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	private final OrderResitoty orderResitoty;
	private final ProductRepository productRepository;
	private final ICartService cartService;
	private final ModelMapper modelMapper;
	
	@Transactional
	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<OrderItem>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order savesOrded = orderResitoty.save(order);
		cartService.clearCart(cart.getId());
		return savesOrded;
	}
	
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList.stream().map(item -> item.getPrice()
						.multiply( new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal :: add);
	}
	
	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	
	private List<OrderItem> createOrderItems(Order order, Cart cart){
		return cart.getItems().stream().map(cartItem ->{
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantaty());
			productRepository.save(product);
			return new OrderItem(
					order,
					product,
					cartItem.getQuantaty(),
					cartItem.getUnitPrice()
					);
		}).toList();	
	  }
	
	

	@Override
	public OrderDto getOrder(Long orderId) {
		return orderResitoty.findById(orderId)
				.map(this :: convertToDto)
				.orElseThrow(()->new ResourceNotFoundException("Order not found"));

	}
	
	@Override
	public List<OrderDto> getUserOrder(Long userId){
		List<Order> orders = orderResitoty.findByUserId(userId);
		return orders.stream().map(this :: convertToDto).toList();
	}
	
	@Override
	public OrderDto convertToDto(Order order) {
		return modelMapper.map(order, OrderDto.class);
			
	}

}
