package com.sbwithdb.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbwithdb.model.Category;
import com.sbwithdb.model.Product;
import com.sbwithdb.model.Supplier;
import com.sbwithdb.service.CategoryService;
import com.sbwithdb.service.SupplierService;

@Component
public class ValidateProduct {
	
	@Autowired
	private  SupplierService supplierService;
	
	@Autowired
	private  CategoryService categoryService;

	public  Map<String, String> check(Product product) {

		Map<String, String> map = new HashMap<>();



			if (product.getProductName() == null) {
				map.put("productName", "Invalid Product Name");
			}

			if (product.getProductQty() < 1) {
				map.put("productQty", "Product Quantity should be greater than 0");
			}

			if (product.getProductPrice() < 1) {
				map.put("productPrice", "Product Price should be greater than 0");
			}
			
			Supplier supplier = supplierService.getSupplierById(product.getSupplierId().getSupplierId());
			
			if(supplier == null) {
				map.put("Supplier Id", "Enter the valid Supplier Id");
			}
			
			Category category = categoryService.getCategoryById(product.getCategoryId().getCategoryId());

			if(category == null) {
				map.put("Category Id", "Enter the valid Category Id");
			}


		return map;

	}
}
