package com.mauro.shop.service.product;

import java.util.List;

import com.mauro.shop.dto.ProductDto;
import com.mauro.shop.model.Product;
import com.mauro.shop.request.AddProductRequest;
import com.mauro.shop.request.ProductUpdateRequest;

public interface IProductService {
	Product addProduct(AddProductRequest product);

	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(ProductUpdateRequest request, Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category,String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand,String name);
	Long countProductByBrandandName(String brand,String name);

	ProductDto convertToDTO(Product product);
	List<ProductDto> getConvertedProducts(List<Product> products);

	
}
