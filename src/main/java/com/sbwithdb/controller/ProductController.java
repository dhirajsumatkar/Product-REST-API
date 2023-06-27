package com.sbwithdb.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbwithdb.exceptions.ProductAlreadyExists;
import com.sbwithdb.exceptions.ProductNotExistException;
import com.sbwithdb.model.FinalProduct;
import com.sbwithdb.model.Product;
import com.sbwithdb.service.ProductService;

import net.bytebuddy.implementation.bytecode.Throw;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService service;

	@PostMapping("/add-product")
	public ResponseEntity<String> saveProduct(@RequestBody @Valid Product product) {

		boolean isAdded = service.saveProduct(product);

		if (isAdded)
			return ResponseEntity.ok("Product Added Succesfully");
		else
			throw new ProductAlreadyExists("Product Already Exists With the name = "+product.getProductName());

	}
	
	
	@PostMapping("/updateSheet")
	public ResponseEntity<Map<String, Object>> uploadSheet(@RequestParam MultipartFile file){
		
		System.out.println(file.getOriginalFilename());
		
		Map<String, Object> map = service.uploadSheet(file);
		
		
		return ResponseEntity.ok(map);
	}

	@GetMapping("/get-All-Products")
	public ResponseEntity<?> getAllProducts() {

		List<Product> list = service.getAllProducts();

		System.out.println("Controller List " + list);

		if (list.isEmpty()) {

			return new ResponseEntity<String>("Product Does Exists", HttpStatus.NOT_FOUND);
		} else {
			return ResponseEntity.ok(list);
		}
	}

	@PutMapping("/update-product")
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {

		boolean isUpdated = service.updateProduct(product);
		if (isUpdated) {
			return ResponseEntity.ok("Product updated Succesfully");
		} else {
			return new ResponseEntity<String>("Product Does Not Found With id ", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/delete-product/{productId}")
	public ResponseEntity<String> deleteeProduct(@PathVariable Long productId) {

		boolean isDeleted = service.deleteProduct(productId);
		if (isDeleted) {
			return ResponseEntity.ok("Product Deleted Succesfully");
		} else {
			return new ResponseEntity<String>("Product Does Not Found With id " + productId, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/get-productById/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId) {
		Product product = service.getProductById(productId);

		if (product != null) {

			return new ResponseEntity(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product Does Not Found With id " + productId, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/sort-products/{sortBy}/{fieldName}")
	public ResponseEntity<?> sortProducts(@PathVariable String sortBy, @PathVariable String fieldName) {
		List<Product> list = service.sortProducts(sortBy, fieldName);

		if (list != null) {

			return new ResponseEntity(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Products Does Not Exits. ", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/get-maxPriceProducts")
	public ResponseEntity<?> getMaxPriceProducts() {
		List<Product> list = service.getMaxPriceProducts();

		if (list != null) {

			return new ResponseEntity(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Products Does Not Exits. ", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/get-totalProductsCount")
	public ResponseEntity<?> getTotalCountOfProducts() {

		Long count = service.getTotalCountOfProducts();

		if (count != null) {

			return new ResponseEntity(count, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Products Does Not Exits. ", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/get-sumOfProductPrice")
	public ResponseEntity<?> countSumOfProductPrice() {

		Double countProductPrice = service.countSumOfProductPrice();

		if (countProductPrice != null) {

			return new ResponseEntity(countProductPrice, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Products Does Not Exits. ", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/get-productByName/{getProductByNam}")
	public ResponseEntity<?> getProductByName(@PathVariable String getProductByNam) {
		
		Product product = service.getProductByName(getProductByNam);
		
		if (product != null) {

			return new ResponseEntity(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product Does Not Exits. ", HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@GetMapping("/get-finalProductById/{productId}")
	public ResponseEntity<?> getFinalProductById(@PathVariable Long productId) {
		FinalProduct product = service.getFinalProductById(productId);

		if(product != null) {

			return new ResponseEntity(product, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Product Does Not Found With id " + productId, HttpStatus.NOT_FOUND);
		}
	}

}
