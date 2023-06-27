package com.sbwithdb.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.sbwithdb.model.FinalProduct;
import com.sbwithdb.model.Product;

public interface ProductService {
	
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
	
	public Map<String,Object> uploadSheet(MultipartFile file);
	
	public FinalProduct getFinalProductById(Long productId);

}
