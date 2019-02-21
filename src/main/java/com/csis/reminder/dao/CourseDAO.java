package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.Course;
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
			List<Course> courses = manager.createQuery("SELECT x FROM Course x Where x = "+user.getId()).getResultList();
			return courses;
		}	
		
		/**
		 * Method to insert (persist) a new course into our database
		 * @param course {@link Course} - object which holds a course's data
		 * 
		 */	
		public void addCourse(Course course) {
			EntityManager manager = Resources.getEntityManager();
			EntityTransaction transaction = manager.getTransaction(); 
			
			try {
			   transaction.begin();
			   manager.persist(course);
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
		public void deleteCourse(Long courseID) {
			EntityManager manager = Resources.getEntityManager();
			EntityTransaction transaction = manager.getTransaction(); 
			try {
			   transaction.begin();
			   Course deleteCourse = manager.find(Course.class, courseID);
			   manager.remove(deleteCourse);
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
		 * Method to update a course into our database
		 * @param courseID {@link CourseID} - object which holds a courseID  (key)
		 * @param updateCourse {@link updateCourse} - object which holds an updated course's data
		 * 
		 */	
		public void updateCourse(Long courseID, Course updateCourse) {
			EntityManager manager = Resources.getEntityManager();
			EntityTransaction transaction = manager.getTransaction(); 
			
			try {
		    	transaction.begin();
			    Course foundCourse = manager.find(Course.class, courseID);
				foundCourse.setCourseName(updateCourse.getCourseName());
			    foundCourse.setCourseInstructor(updateCourse.getCourseInstructor());
			    foundCourse.setStartDate(updateCourse.getStartDate());
			    foundCourse.setEndDate(updateCourse.getEndDate());
			     manager.merge(foundCourse);
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
		
 }

	
	
	
