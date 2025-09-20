package com.mauro.shop.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
	private Long Id;
	private BigDecimal totalAmount = BigDecimal.ZERO;

	private Set<CarItemDto> items = new HashSet<CarItemDto>();
}
