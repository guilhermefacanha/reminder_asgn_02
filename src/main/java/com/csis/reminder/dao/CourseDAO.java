package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.Course;


	/**
	 * 
	 * @author Reminder Group
	 * Class is responsible for managing the course Data Access Object
	 * it contains the methods which allow us to perform CRUD operations 
	 *
	 */
	public class CourseDAO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7385285317285391401L;

	
		/**
		 * Method to run a first query to initialize the database connection context
		 */
		public void initSelec() {
			EntityManager manager = Resources.getEntityManager();
			manager.find(Course.class, new Long(1));
		}

		@SuppressWarnings("unchecked")
		public List<Course> getAllCourses() {
			EntityManager manager = Resources.getEntityManager();
			List<Course> users = manager.createQuery("SELECT x FROM Course x").getResultList();
			return users;
		}	
		
		/**
		 * Method to insert (persist) a new user into our database
		 * @param user {@link User} - object which holds a user's data
		 * its information is gathered in the Register class
		 */	
		public void addCourse(Course course) {
			EntityManager manager = Resources.getEntityManager();
			EntityTransaction transaction = manager.getTransaction(); 
			transaction.begin();
			manager.persist(course);
			transaction.commit();
		}

	
		/**
		 * Method to check if a courseName  + educationalInstitution is already registered in database
		 * @param courseName - {@link String} user email
		 * @return {@link Boolean} if courseName  + educationalInstitution already exists on database
		 */
		public boolean courseNameInstitutionExists(String course, String institution) {
			EntityManager manager = Resources.getEntityManager();
			try {
				manager.createQuery("SELECT x FROM Course x WHERE x.courseName = :course AND x.educationalInstitution = :institution").setParameter(" course", course).setParameter(" Educational Institution", institution).getSingleResult();
				return true;
			} catch (NoResultException e) {
				return false;
			}
		}
 }

	
	
	
