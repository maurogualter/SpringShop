package com.mauro.shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mauro.shop.exeptions.AlreadyExistException;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Category;
import com.mauro.shop.response.ApiResponse;
import com.mauro.shop.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	private final ICategoryService categoryService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.gelAllCategories();
			return ResponseEntity.ok(new ApiResponse("Found!", categories));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Erro:", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
		try {
			Category theCategory = categoryService.addCategory(name);
			return ResponseEntity.ok(new ApiResponse("Success", theCategory));
		} catch (AlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	

	@GetMapping("/category/{categoryId}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
		try {
			Category theCategory = categoryService.getCategoryById(categoryId);
			return ResponseEntity.ok(new ApiResponse("Found", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@GetMapping("/category/by/name/{name}/category")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		try {
			Category theCategory = categoryService.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("Found", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@DeleteMapping("/category/{name}/delete")
	public ResponseEntity<ApiResponse> deleteCategoryByName(@PathVariable Long categoryId){
		try {
			categoryService.deleteCategoryById(categoryId);
			return ResponseEntity.ok(new ApiResponse("Category Deleted success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@PutMapping("/category/{categoryId}/update")
	public ResponseEntity<ApiResponse> updateCategoryByName(@PathVariable Long categoryId, @RequestBody Category category){
		try {
			Category updateCategory = categoryService.updatedCategory(category, categoryId);
			return ResponseEntity.ok(new ApiResponse("Update Success!", updateCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	

}
