package com.mauro.shop.service.category;

import java.util.List;

import com.mauro.shop.model.Category;

public interface ICategoryService {
	Category getCategoryById(Long ID);
	Category getCategoryByName(String name);
	List<Category> gelAllCategories();
	Category addCategory(Category category);
	Category updatedCategory(Category category, Long Id);
	void deleteCategoryById(Long id);
	
}
