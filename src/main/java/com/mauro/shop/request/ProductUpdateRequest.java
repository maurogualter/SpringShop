package com.mauro.shop.request;

import java.math.BigDecimal;

import com.mauro.shop.model.Category;

import lombok.Data;

@Data
public class ProductUpdateRequest {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;

	private Category category;
}
