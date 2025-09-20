package com.mauro.shop.service.order;

import java.util.List;

import com.mauro.shop.dto.OrderDto;
import com.mauro.shop.model.Order;

public interface IOrderService {

	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	public List<OrderDto> getUserOrder(Long userId);
	public OrderDto convertToDto(Order order) ;
}
