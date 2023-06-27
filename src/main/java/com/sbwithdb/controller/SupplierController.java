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
import com.sbwithdb.model.Supplier;
import com.sbwithdb.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService service;

	@PostMapping("add-supplier")
	public ResponseEntity<String> saveSupplier(@RequestBody Supplier supplier) {

		boolean isAdded = service.saveSupplier(supplier);

		if (isAdded)
			return ResponseEntity.ok("Supplier Added Succesfully");
		else
			return new ResponseEntity<String>("Supplier already Exists with the id " + supplier.getSupplierId(),
					HttpStatus.CONFLICT);

	}

	@GetMapping("/get-supplierById/{id}")
	public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {

		Supplier supplier = service.getSupplierById(id);

		if (supplier != null) {

			return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
		} else {
			return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/get-supplierByName/{getSupplierByName}")
	public ResponseEntity<?> getSupplierByName(@PathVariable String getSupplierByName) {

		Supplier supplier = service.getSupplierByName(getSupplierByName);

		if (supplier != null) {

			return new ResponseEntity(supplier, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("supplier Does Not Exits. ", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/get-allSupplier")
	public ResponseEntity<?> getAllSupplier() {
		
		List<Supplier> supplier = service.getAllSupplier();
		
		if (supplier != null) {

			return new ResponseEntity(supplier, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Supplier Does Not Exits. ", HttpStatus.NOT_FOUND);
		}
		
	}	

}
