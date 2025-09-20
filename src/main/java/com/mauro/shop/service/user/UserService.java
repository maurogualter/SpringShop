package com.mauro.shop.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mauro.shop.dto.UserDto;
import com.mauro.shop.exeptions.AlreadyExistException;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.User;
import com.mauro.shop.repository.UserRepository;
import com.mauro.shop.request.CreateUserRequest;
import com.mauro.shop.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
	}

	@Override
	public User creatUser(CreateUserRequest request) {
		return Optional.of(request)
				.filter(user -> !userRepository.existsByEmail(request.getEmail()))
				.map(req ->{ 
					return userRepository.save(createUser(req));
					})
				.orElseThrow(()-> new AlreadyExistException("Oops! "+request.getEmail()+" already exist!"));
		
	}
	
	private User createUser(CreateUserRequest request) {
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		return user;
	}

	@Override
	public User updateUser(UpdateUserRequest request, Long userId) {
		return userRepository.findById(userId).map(existingUser ->{
			existingUser.setFirstName(request.getFirstName());
			existingUser.setLastName(request.getLastName());
			return userRepository.save(existingUser);
		}).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository :: delete,
				()-> new ResourceNotFoundException("User not found!"));
		
	}
	
	@Override
	public UserDto convertUserToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}
}
