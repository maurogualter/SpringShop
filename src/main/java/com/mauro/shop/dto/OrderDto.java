package com.mauro.shop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class OrderDto {
	private Long orderId;
	private Long userid;
	private LocalDate orderDate;
	private BigDecimal totalAmount;
	private String status;
	private Set<OrderItemDto> items = new HashSet<OrderItemDto>();	
	

}
