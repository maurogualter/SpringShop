package com.mauro.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mauro.shop.dto.UserDto;
import com.mauro.shop.exeptions.AlreadyExistException;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.User;
import com.mauro.shop.request.CreateUserRequest;
import com.mauro.shop.request.UpdateUserRequest;
import com.mauro.shop.response.ApiResponse;
import com.mauro.shop.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	private final IUserService userService;
	
	@GetMapping("/user/{userId}/user")
	public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId){
		try{
			User user = userService.getUserById(userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("User Success.", userDto));
		}catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	
	@PostMapping("/user")
	public ResponseEntity<ApiResponse> creatUser(@RequestParam CreateUserRequest request){
		try{
			User user = userService.creatUser(request);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Create user Success.", userDto));
		}catch (AlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	
	@PutMapping("/user/{userId}/update)")
	public ResponseEntity<ApiResponse> updateUser(@RequestParam UpdateUserRequest request,@PathVariable Long userId) {
		try{
			User user = userService.updateUser(request, userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Update user Success.", userDto));
		}catch (AlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	
	
	
	@DeleteMapping("user/{userId}/delete")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		try{
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("Delete User Success.", null));
		}catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	

	
}
