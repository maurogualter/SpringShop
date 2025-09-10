package com.mauro.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mauro.shop.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findByProductId(Long productId);
}
