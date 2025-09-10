package com.mauro.shop.exeptions;

public class AlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = -3208045337120947454L;

	public AlreadyExistException(String message) {
		super(message);
	}
}
