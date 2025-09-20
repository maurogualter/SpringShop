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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mauro.shop.dto.ProductDto;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Product;
import com.mauro.shop.request.AddProductRequest;
import com.mauro.shop.request.ProductUpdateRequest;
import com.mauro.shop.response.ApiResponse;
import com.mauro.shop.service.product.IProductService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	private final IProductService productService;
	

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
		return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
		
	}
	
	@GetMapping("/product/{productId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
		try {
			Product product = productService.getProductById(productId);
			var productDto = productService.convertToDTO(product);
			return ResponseEntity.ok(new ApiResponse("Success", productDto));
		}catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/{name}/products")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> products = productService.getProductsByName(name);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));
			}
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product TheProduct = productService.addProduct(product);
			var productDto = productService.convertToDTO(TheProduct);
			return ResponseEntity.ok(new ApiResponse("Add product success", productDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/product/{productId}/update")
	public ResponseEntity<ApiResponse> updateProduct (@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
		try{
			Product TheProduct  = productService.updateProduct(request, productId);
			var productDto = productService.convertToDTO(TheProduct);
			return ResponseEntity.ok(new ApiResponse("Update product success", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	} 
	
	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Delete Product Success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/products/by/brand-and-name/")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));
			}
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/products/by/category-and-name")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brandName){
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(category, brandName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));
			}
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse> getProductBrand(@RequestParam  String brandName){
		try {
			List<Product> products = productService.getProductsByBrand(brandName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));
			}
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/{category}/all/product")
	public ResponseEntity<ApiResponse> getProductCategory(@PathVariable String category){
		try {
			List<Product> products = productService.getProductsByCategory(category);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));
			}
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success", convertedProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/cont/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductByBrandandName(@RequestParam String brand, @RequestParam String name){
		try {
			var producrCount = productService.countProductByBrandandName( brand, name);
			return ResponseEntity.ok(new ApiResponse("Success", producrCount));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
