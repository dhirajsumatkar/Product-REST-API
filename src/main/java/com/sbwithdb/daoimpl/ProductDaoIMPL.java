package com.sbwithdb.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.sbwithdb.controller.ProductController;
import com.sbwithdb.dao.ProductDao;
import com.sbwithdb.exceptions.GlobalExceptionHandler;
import com.sbwithdb.model.Product;
import com.sbwithdb.utility.ValidateProduct;

@Repository
public class ProductDaoIMPL implements ProductDao {

	@Autowired
	SessionFactory factory;

	@Override
	public boolean saveProduct(Product product) {

		boolean isAdded = true;
		Session session = null;

//		Map<String,Object> map=new HashMap<String, Object>();
//		
//		map.put("Uploaded Records In DB", product.getProductName());
//		map.put("Already Exists records in DB", product.getProductPrice());
//		System.out.println(map);

		try {

			session = factory.openSession();
			session.save(product);
			session.beginTransaction().commit();

		} catch (PersistenceException e) {
			isAdded = false;
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
	public List<Product> getAllProducts() {

		Session session = null;
		List<Product> list = null;

		try {

			session = factory.openSession();
			Criteria criteria = session.createCriteria(Product.class);
			list = criteria.list();

		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public boolean updateProduct(Product productReceived) {
		boolean isUpdated = true;
		Session session = null;

		try {
			session = factory.openSession();

			session.update(productReceived);
			session.beginTransaction().commit();
			isUpdated = true;

		} catch (Exception e) {
			isUpdated = false;

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isUpdated;

	}

	@Override
	public boolean deleteProduct(Long productId) {

		boolean isDeleted = true;
		Session session = null;

		try {
			session = factory.openSession();
			Product product = session.get(Product.class, productId);

			if (product != null) {
				session.delete(product);
				session.beginTransaction().commit();
				isDeleted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			isDeleted = false;

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isDeleted;
	}

	@Override
	public Product getProductById(Long productId) {

		Session session = null;
		Product product = null;

		try {
			session = factory.openSession();
			product = session.get(Product.class, productId);
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return product;
	}

	@Override
	public List<Product> sortProducts(String sortBy, String fieldName) {

		Session session = null;
		List<Product> list = null;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(Product.class);

			if (sortBy.equalsIgnoreCase("asc")) {
				criteria.addOrder(Order.asc(fieldName));
			} else {
				criteria.addOrder(Order.desc(fieldName));
			}

			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Product> getMaxPriceProducts() {

		Session session = null;
		List<Product> list = null;
		Double maxPrice = 0d;
		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.max("productPrice"));
			List list2 = criteria.list();
			System.out.println(list2);

			if (!list2.isEmpty()) {
				maxPrice = (Double) list2.get(0);
			}

			Criteria criteria2 = session.createCriteria(Product.class);
			criteria2.add(Restrictions.eq("productPrice", maxPrice));
			list = criteria2.list();
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public Double countSumOfProductPrice() {

		Session session = null;
		Double productPriceSum = 0d;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(Product.class);

			productPriceSum = (Double) criteria.setProjection(Projections.sum("productPrice")).uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return productPriceSum;
	}

	@Override
	public Long getTotalCountOfProducts() {

		Session session = null;
		Long count = 0l;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(Product.class);

			count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return count;
	}

	@Override
	public Product getProductByName(String getProductByName) {

		Session session = null;
		Product product = null;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.add(Restrictions.eq("productName", getProductByName));
			product = (Product) criteria.uniqueResult();
		} catch (Exception e) {

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return product;
	}

	@Override
	public Map<String, Object> uploadProdcuts(List<Product> list) {

		Map<String, Object> map = new LinkedHashMap<>();

		List<Integer> row = new ArrayList<>();

		int addedCount = 0;
		int alreadyExistsCount = 0;
		int rowNum = 2;
		int totalExcludeded = 1;
		int totalRecord = 0;

		Map<String, Map<String, String>> in = new HashMap<>();

		System.out.println(in);

//		for (Product product : list) {
//			totalRecord++;
//			
//			Map<String, String> isValid = ValidateProduct.check(product);
//			
//			if (isValid.isEmpty()) {
//
//			Product productByName = getProductByName(product.getProductName());
//
//
//			if (productByName == null) {
//
//				Boolean isAdded = saveProduct(product);
//
//				if (isAdded) {
//					addedCount++;
//			 	}
//
//
//		} else {
//			alreadyExistsCount = alreadyExistsCount + 1;
//			row.add(rowNum);
//		}
//
//			rowNum = rowNum + 1;
//		}else {
//			in.put(String.valueOf(rowNum), isValid);
//			totalExcludeded = totalExcludeded + 1;
//			rowNum = rowNum + 1;     
//		}
//
//		map.put("Uploaded Records in DB ", addedCount);
//		map.put("Total exists Records in DB ", alreadyExistsCount);
//
//		map.put("Row Num,Exists Record in DB", row);
//
//		
//		map.put("Total Records in Sheet", totalRecord);
//		map.put("Total excluded", totalExcludeded-1);
//
//		if (in != null) {
//
//			map.put("Bad records row number", in);
//
//		}
//		}

		return map;

	}
	

}
