package com.sbwithdb.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbwithdb.dao.CategoryDao;
import com.sbwithdb.model.Category;



@Repository
public class CategoryDaoIMPL implements CategoryDao{
	
	@Autowired
	SessionFactory factory;

	@Override
	public boolean saveCategory(Category category) {
		
		boolean isAdded = true;
		Session session = null;

		try {

			session = factory.openSession();
			session.save(category);
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
	public Category getCategoryById(Long id) {
		
		Category product=null;
		Session session = null;

		try {

			session = factory.openSession();
		    product = session.get(Category.class, id);
			

		} catch (Exception e) {
			e.printStackTrace();
			

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return product;
	}

	@Override
	public Category getCategoryByName(String name) {
		
		Category category=null;
		Session session = null;

		try {

			session = factory.openSession();
			category= (Category) session.createCriteria(Category.class)
		    .add(Restrictions.eq("categoryName", name))
		    .uniqueResult();
			

		} catch (Exception e) {
			e.printStackTrace();
			

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return category;
	}

	@Override
	public List<Category> getAllCAtegory() {

		Session session = null;
		List<Category> list=null;

		try {

			session = factory.openSession();
			list=session.createCriteria(Category.class).list();
			

		} catch (Exception e) {
			e.printStackTrace();
			

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return list;
	}

}
