package com.sbwithdb.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbwithdb.dao.CategoryDao;
import com.sbwithdb.model.Category;
import com.sbwithdb.service.CategoryService;



@Service
public class CategoryServiceIMPL implements CategoryService{

	@Autowired
	private CategoryDao dao;

	@Override
	public boolean saveCategory(Category category) {
		boolean isAdded = dao.saveCategory(category);
		return isAdded;
	}

	@Override
	public Category getCategoryById(Long id) {
		Category product = dao.getCategoryById(id);
		return product;
	}

	@Override
	public Category getCategoryByName(String name) {
		Category category = dao.getCategoryByName(name);
		return category;
	}

	@Override
	public List<Category> getAllCAtegory() {
		List<Category> list = dao.getAllCAtegory();
		return list;
	}
	
	
}
