package com.sbwithdb.service;

import java.util.List;

import com.sbwithdb.model.Category;

public interface CategoryService {

	public boolean saveCategory(Category category);
	
	public Category getCategoryById(Long id);
	public Category getCategoryByName(String name);
	public List<Category> getAllCAtegory();
}
