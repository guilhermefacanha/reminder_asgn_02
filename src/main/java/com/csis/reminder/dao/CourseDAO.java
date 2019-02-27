package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.User;


	/**
	 * 
	 * @author Reminder Group
	 * Class is responsible for managing the course Data Access Object
	 * it contains the methods which allows users to perform CRUD operations 
	 * on their courses
	 */
	public class CourseDAO implements Serializable {

		
		private static final long serialVersionUID = 7385285317285391401L;
	
		/**
		 * Method to run a first query to initialize the database connection context
		 */
		public void initSelec() {
			EntityManager manager = Resources.getEntityManager();
			manager.find(Course.class, new Long(1));
		}

		@SuppressWarnings("unchecked")
		public List<Course> getAllCourses(User user) {
			EntityManager manager = Resources.getEntityManager();
	
		     List<Course> courses = manager.createQuery("SELECT  x FROM Course x WHERE x.user.id ="+user.getId()).getResultList();
		  
		//	System.out.println("u " + user.getId());
		//	for (int i=0; i<courses.size(); i++){
		//		   System.out.println("Element "+i+courses.get(i));
		//		}
			
			return courses;
		}	
		
		/**
		 * Method to insert (persist) a new course into our database or 
		 * update (merge ) a course into our database
		 * @param course {@link Course} - object which holds a course's data
		 * 
		 */	
		public void saveCourse(Course course) {
			EntityManager manager = Resources.getEntityManager();
			EntityTransaction transaction = manager.getTransaction(); 
			
		
			try {
			   transaction.begin();
			   if (course.getId()>0)  manager.merge(course) ;
			   else                   manager.persist(course);
			   transaction.commit();
			}
			catch (RuntimeException e) {
			    if (transaction != null) transaction.rollback();
			    throw e; 
			}
			finally {
			    manager.close();
			}
		}

		/**
		 * Method to delete a course into our database
		 * @param courseID {@link CourseID} - object which holds a courseID  (key)
	     */	
		public void deleteCourse(Long id) {
			EntityManager manager = Resources.getEntityManager();
			EntityTransaction transaction = manager.getTransaction(); 
			
	        // tem que excluir os eventos  habilitar o cascade 
			
			try {
				manager.getTransaction().begin();
				manager.createQuery("DELETE FROM Course x WHERE x.id = :id").setParameter(" CourseID ", id).executeUpdate();
				manager.getTransaction().commit();
			}
			catch (RuntimeException e) {
			    if (transaction != null) transaction.rollback();
			    throw e; 
			}
			finally {
			    manager.close();
			}
			
		}
		
        // get  courseID
		public Course getCourse(long id) {
			EntityManager manager = Resources.getEntityManager();
			return manager.find(Course.class, id);
		}
		
 }

	
	
	
