package com.sbwithdb.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbwithdb.dao.SupplierDao;
import com.sbwithdb.model.Supplier;

@Repository
public class SupplierDaoIMPL implements SupplierDao {

	@Autowired
	SessionFactory factory;

	@Override
	public boolean saveSupplier(Supplier supplier) {

		boolean isAdded = true;
		Session session = null;

		try {

			session = factory.openSession();
			session.save(supplier);
			session.beginTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			isAdded = false;

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isAdded;
	}

	@Override
	public Supplier getSupplierById(Long id) {

		Supplier supplier = null;
		Session session = null;

		try {

			session = factory.openSession();
			supplier = session.get(Supplier.class, id);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return supplier;
	}

	@Override
	public Supplier getSupplierByName(String name) {
		
		Supplier supplier = null;
		Session session = null;

		try {

			session = factory.openSession();
			supplier=(Supplier) session.createCriteria(Supplier.class)
			.add(Restrictions.eq("supplierName", name))
			.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return supplier;
	}

	@Override
	public List<Supplier> getAllSupplier() {
		
		List<Supplier> supplier = null;
		Session session = null;

		try {

			session = factory.openSession();
			supplier=session.createCriteria(Supplier.class).list();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return supplier;
	}

}
