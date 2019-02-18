package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.User;

/**
 * 
 * @author Reminder Group
 * Class is responsible for managing the Event Data Access Object
 * it contains the methods which allows users to perform CRUD operations on 
 * their events
 */


public class EventDAO implements Serializable {

	private static final long serialVersionUID = 5584599198950863626L;
		
	/**
	 * Method to insert (persist) a new event into our database
	 * @param Event {@link Event} - object which holds an event's data
	 */	
	public void addEvent(Event event) {
		EntityManager manager = Resources.getEntityManager();
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();
		manager.persist(event);
		transaction.commit();
	}
	
	/**
	 * Method which retrieves all the events of a user and returns a list of them
	 * @param user - provides the user which the program will load the events from
	 * @return - returns a list of Event objects from that user
	 */
	@SuppressWarnings("unchecked")
	public List<Event> getAllEvents(User user) {
		EntityManager manager = Resources.getEntityManager();
		List<Event> events = manager.createQuery("SELECT x FROM Event x").getResultList();
		return events;
	}
	
	/**
	 * Method which retrieves all the events of a user, filtered by a course
	 * @param user - provides the user which the program will load the events from
	 * @return - returns a list of Event objects from that user
	 */
	@SuppressWarnings("unchecked")
	public List<Event> getAllEventsByCourse(User user, Course course) {
		EntityManager manager = Resources.getEntityManager();
		List<Event> events = manager.createQuery("SELECT x FROM Event x WHERE Course = " + course).getResultList(); // Where course = ?
		return events;
	}
	
	
	
}
