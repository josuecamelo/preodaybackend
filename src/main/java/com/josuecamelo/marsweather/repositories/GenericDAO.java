package com.josuecamelo.marsweather.repositories;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

public class GenericDAO<T> {
	public static <T> void save(T entity) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(entity);
		EntityManagerHelper.commit();		
	}
	
	public static <T> void saveWithoutTransaction(T entity) {		
		EntityManagerHelper.getEntityManager().persist(entity);		
	} 
	
	public static <T> void saveAll(List<T> entities) {
		EntityManagerHelper.beginTransaction();
		int i=0;
		Session session = EntityManagerHelper.getEntityManager().unwrap(Session.class);
		for (T t : entities) {			
			saveWithoutTransaction(t);
			if ( i % 20 == 0 ) {				
				session.flush();
		        session.clear();
			}
			i++;
		}
		EntityManagerHelper.commit();
	}

	public static <T> void update(T entity) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().merge(entity);
		EntityManagerHelper.commit();
	}
	
	public static <T> void delete(T entity) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().remove(entity);
		EntityManagerHelper.commit();
	}
	
	public static <T> void deleteAll(Class<T> persistedClass) {
		Session session = EntityManagerHelper.getEntityManager().unwrap(Session.class);
		EntityManagerHelper.beginTransaction();
		session.createQuery("delete from "+persistedClass.getName()).executeUpdate();
		EntityManagerHelper.commit();
	}
	
	public static <T> List<T> findAll(Class<T> persistedClass) {	
		EntityManagerHelper.getEntityManager().clear();
		CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		query.from(persistedClass);
		return EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
	} 
	
	public static <T> T findById(Class<T> persistedClass, Long fieldValue) {		
		return findByField(persistedClass, "id", fieldValue);
	}  	
	
	public static <T> T findByField(Class<T> persistedClass, String fieldName, Long fieldValue) {
		EntityManagerHelper.getEntityManager().clear();
		CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);	   
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.equal(root.get(fieldName), fieldValue));
		return EntityManagerHelper.getEntityManager().createQuery(criteria).getSingleResult();
	}
	
	public static <T> T findByField(Class<T> persistedClass, String fieldName, String fieldValue) {
		EntityManagerHelper.getEntityManager().clear();
		CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);	   
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.equal(root.get(fieldName), fieldValue));
		return EntityManagerHelper.getEntityManager().createQuery(criteria).getSingleResult();
	}
	
	public static <T> List<T> findListByField(Class<T> persistedClass, String fieldName, String fieldValue) {
		EntityManagerHelper.getEntityManager().clear();
		CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);	   
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.equal(root.get(fieldName), fieldValue));
		return EntityManagerHelper.getEntityManager().createQuery(criteria).getResultList();
	}
	
	public static <T> List<T> findListByField(Class<T> persistedClass, String fieldName, Long fieldValue) {
		EntityManagerHelper.getEntityManager().clear();
		CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);	   
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.equal(root.get(fieldName), fieldValue));
		return EntityManagerHelper.getEntityManager().createQuery(criteria).getResultList();
	}
}

