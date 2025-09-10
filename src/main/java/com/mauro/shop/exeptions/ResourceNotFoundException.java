package com.mauro.shop.exeptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2633664191759090152L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
