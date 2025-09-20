package com.mauro.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mauro.shop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

}
