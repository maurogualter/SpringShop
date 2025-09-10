package com.mauro.shop.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mauro.shop.dto.ImageDto;
import com.mauro.shop.dto.ProductDto;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Category;
import com.mauro.shop.model.Image;
import com.mauro.shop.model.Product;
import com.mauro.shop.repository.CategoryRepository;
import com.mauro.shop.repository.ImageRepository;
import com.mauro.shop.repository.ProductRepository;
import com.mauro.shop.request.AddProductRequest;
import com.mauro.shop.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	 private final ProductRepository productRepository;
	 private final CategoryRepository categoryRepository;
	 private final ImageRepository imageRepository;
	 private final ModelMapper modelMapper;
	@Override
	public Product addProduct(AddProductRequest request) {
		// check if category is found
		//if yes set as a new category
		//if no save it as a new category then set it.
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(creatProduct(request, category));
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
		return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete,
				 ()->  {throw new ResourceNotFoundException("Product not found!");});
		
	}


	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request))
				.map(productRepository :: save)
				.orElseThrow(()->new ResourceNotFoundException("Product not found!"));
		
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		
		return existingProduct;				
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
	
	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products){
		return products.stream().map(this::convertToDTO).toList();
	}
	
	@Override
	public ProductDto convertToDTO(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imagesDto = images.stream().map(
				image -> modelMapper.map(image, ImageDto.class))
				.toList();
		productDto.setImages(imagesDto);
		return productDto;
	}

}
