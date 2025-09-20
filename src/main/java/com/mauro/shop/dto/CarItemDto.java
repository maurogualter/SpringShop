package com.mauro.shop.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CarItemDto {
	private Long Id;
	private int quantaty;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	
	private ProductDto product;
}
