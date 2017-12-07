package io.github.wilsontheory.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.wilsontheory.model.Cat;

@Transactional
@Repository //good practice
public class HibernateDao {
	
	private SessionFactory sessionFactory;
	
	public void insertCat(Cat thisCat){
		try {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.persist(thisCat);
			tx.commit();
			session.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}

}
