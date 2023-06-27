package com.sbwithdb.dao;

import java.util.List;

import com.sbwithdb.model.Category;

public interface CategoryDao {

	public boolean saveCategory(Category category);
	
	public Category getCategoryById(Long id);
	public Category getCategoryByName(String name);
	public List<Category> getAllCAtegory();
}
