package com.mauro.shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mauro.shop.dto.OrderDto;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Order;
import com.mauro.shop.response.ApiResponse;
import com.mauro.shop.service.order.IOrderService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	private final IOrderService orderService;
	
	@PostMapping("/order")
	public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
		try{
			Order order = orderService.placeOrder(userId);
			OrderDto orderDto = orderService.convertToDto(order);
			return ResponseEntity.ok(new ApiResponse("Order Success.", orderDto));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Erro Occured",e.getMessage()));
		}
	}
	
	@GetMapping("/{orderId}/order")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
		try{
			OrderDto order = orderService.getOrder(orderId);
			return ResponseEntity.ok(new ApiResponse("Order Success.", order));
		}catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	
	@GetMapping("/{userId}/order")
	public ResponseEntity<ApiResponse> getUserOrder(@PathVariable Long userId){
		try{
			List<OrderDto> orderes = orderService.getUserOrder(userId);
			return ResponseEntity.ok(new ApiResponse("Order Success.", orderes));
		}catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	
	
}
