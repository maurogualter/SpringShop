package com.mauro.shop.service.user;

import com.mauro.shop.dto.UserDto;
import com.mauro.shop.model.User;
import com.mauro.shop.request.CreateUserRequest;
import com.mauro.shop.request.UpdateUserRequest;

public interface IUserService {
	User getUserById(Long userId);
	User creatUser(CreateUserRequest request);
	User updateUser(UpdateUserRequest request, Long userId);
	void deleteUser(Long userId);
	public UserDto convertUserToDto(User user);
}
