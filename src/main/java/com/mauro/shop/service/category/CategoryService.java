package com.mauro.shop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mauro.shop.exeptions.AlreadyExistException;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Category;
import com.mauro.shop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
	private final CategoryRepository  categoryRepository; 
	
	@Override
	public Category getCategoryById(Long ID) {
		return categoryRepository.findById(ID)
				.orElseThrow(()-> new ResourceNotFoundException("Category not fond"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> gelAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category).filter(c-> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository :: save).orElseThrow(() -> new AlreadyExistException(category.getName()+" already exist"));
	}

	@Override
	public Category updatedCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory ->{ 
			    oldCategory.setName(category.getName()); 
				return categoryRepository.save(oldCategory);
			 }).orElseThrow(()-> new ResourceNotFoundException("Category not fond!"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		 categoryRepository.findById(id)
		 .ifPresentOrElse(categoryRepository :: delete,
		  ()-> { 
			  throw new ResourceNotFoundException("Category not fond");
		  });
		
	}

}
