package com.mauro.shop.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mauro.shop.exeptions.ProductNotFoundException;
import com.mauro.shop.model.Category;
import com.mauro.shop.model.Product;
import com.mauro.shop.repository.ProductRepository;
import com.mauro.shop.request.AddProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	 private final ProductRepository productRepository;
	@Override
	public Product addProduct(AddProductRequest product) {
		// check if category is found
		//if yes set as a new category
		//if no save it as a new category then set it.
		return null;
	}
	
	private  Product creatProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
				
				);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete,
				 ()->  {throw new ProductNotFoundException("Product not found!");});
		
	}

	@Override
	public void updateProduct(Product product, Long productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductByBrandandName(String brand, String name) {
		return productRepository.countByBrandAndName(brand,name);
	}

}
