package com.sbwithdb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.sbwithdb.model.Product;

public interface ProductDao {

	public boolean saveProduct(Product product);

	public List<Product> getAllProducts();

	public boolean updateProduct(Product product);

	public boolean deleteProduct(Long productId);

	public Product getProductById(Long productId);

	public List<Product> sortProducts(String sortBy, String fieldName);

	public List<Product> getMaxPriceProducts();

	public Double countSumOfProductPrice();

	public Long getTotalCountOfProducts();

	public Product getProductByName(String getProductByName);
	
	public Map<String,Object> uploadProdcuts(List<Product> product);
	
	

}
