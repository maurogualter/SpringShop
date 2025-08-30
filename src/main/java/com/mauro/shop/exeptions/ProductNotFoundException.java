package com.mauro.shop.exeptions;

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -8527779847537967654L;

	public ProductNotFoundException(String message) {
		super(message);
		
	}
}
