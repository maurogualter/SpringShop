package com.mauro.shop.request;

import java.util.List;

import com.mauro.shop.model.Cart;
import com.mauro.shop.model.Order;

import lombok.Data;

@Data
public class CreateUserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
}
