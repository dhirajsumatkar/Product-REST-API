package com.sbwithdb.service;

import java.util.List;

import com.sbwithdb.model.Supplier;

public interface SupplierService {

	public boolean saveSupplier(Supplier supplier);

	public Supplier getSupplierById(Long id);

	public Supplier getSupplierByName(String name);

	public List<Supplier> getAllSupplier();
}
