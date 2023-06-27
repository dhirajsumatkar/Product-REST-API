package com.sbwithdb.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbwithdb.dao.SupplierDao;
import com.sbwithdb.model.Supplier;
import com.sbwithdb.service.SupplierService;

@Service
public class SupplierServiceIMPL implements SupplierService{
	
	@Autowired
	private SupplierDao dao;

	@Override
	public boolean saveSupplier(Supplier supplier) {
		boolean isAdded = dao.saveSupplier(supplier);
		return isAdded;
	}

	@Override
	public Supplier getSupplierById(Long id) {
	    Supplier supplier = dao.getSupplierById(id);
		return supplier;
	}

	@Override
	public Supplier getSupplierByName(String name) {
		Supplier supplier = dao.getSupplierByName(name);
		return supplier;
	}

	@Override
	public List<Supplier> getAllSupplier() {
		
		List<Supplier> list = dao.getAllSupplier();
		return list;
	}

}
