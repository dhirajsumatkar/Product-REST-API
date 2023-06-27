package com.sbwithdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbwithdb.model.Category;
import com.sbwithdb.model.Product;
import com.sbwithdb.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@PostMapping("add-category")
	public ResponseEntity<String> saveCategory(@RequestBody Category category) {

		boolean isAdded = service.saveCategory(category);

		if (isAdded)
			return ResponseEntity.ok("Product Added Succesfully");
		else
			return new ResponseEntity<String>("Product already Exists with the name " + category.getCategoryId(),
					HttpStatus.CONFLICT);

	}

	@GetMapping("/get-categoryById/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {

		Category category = service.getCategoryById(id);

		if (category != null) {

			return new ResponseEntity<Category>(category, HttpStatus.OK);
		} else {
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/get-categoryByName/{getCategoryByName}")
	public ResponseEntity<?> getCategoryByName(@PathVariable String getCategoryByName) {

		Category category = service.getCategoryByName(getCategoryByName);

		if (category != null) {

			return new ResponseEntity(category, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("category Does Not Exits. ", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/get-allCategory")
	public ResponseEntity<?> getAllCAtegory() {
		
		List<Category> category = service.getAllCAtegory();
		
		if (category != null) {

			return new ResponseEntity(category, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Category Does Not Exits. ", HttpStatus.NOT_FOUND);
		}
		
		
	}

}
