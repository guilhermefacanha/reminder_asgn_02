package com.csis.reminder.crud.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.csis.reminder.dao.CourseDAO;
import com.csis.reminder.dao.EventDAO;
import com.csis.reminder.dao.NotificationDAO;
import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.Notification;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.EventType;
import com.csis.reminder.util.ScreenUtil;

@TestInstance(Lifecycle.PER_CLASS)
class NotificationCrudTest {

	User user;
	Course course;
	Event event;
	Notification notification;
	UserDAO userDAO;
	CourseDAO courseDAO;
	EventDAO eventDAO;
	NotificationDAO notificationDAO;
	
	DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_FORMAT);
	
	@BeforeAll
	void setupDAOs()	{
		eventDAO = new EventDAO();
		userDAO = new UserDAO();
		courseDAO = new CourseDAO();
		notificationDAO = new NotificationDAO();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
		user.setId(1);
		System.out.println("User object created!!");
		userDAO.saveUser(user);
		System.out.println("User stored in db!!");
		course = new Course();
		course.setId(1);
		course.setUser(user);
		System.out.println("Course object created!!");
		courseDAO.saveCourse(course);
		System.out.println("Course stored in db!!");
		event = new Event();
		event.setId(1);
		System.out.println("Event object created!!");
		
		
		try	{
			//creates dummy event
			
			event.setCourse(course);
			event.setEventName("Quiz 1");
			event.setDate(formatter.parse("03/25/2019"));
			event.setType(EventType.QUIZ);
			event.setDescription("First quiz of the semester!");
			
			eventDAO.save(event);
			System.out.println("Event stored in db!!");
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}	
		
		
	}

	@Test
	void testNotificationCrud() {
		
		testNotificationCreate();
		testNotificationUpdate();
		testNotificationDelete();
	}
	
	private void testNotificationCreate()	{
		
		try	{
			// creates dummy notification
			notification = new Notification();
			notification.setId(1);
			notification.setEvent(event);
			notification.setChecked(false);
			notification.setDate(formatter.parse("03/18/2019"));
			notification.setNotificationName("Dont forget me");
			
			notificationDAO.save(notification);
			// checks if the notification has been stored in the db by comparing the object's data with data retrieved from the db
			assertTrue(notificationDAO.getNotification(1).getId() == notification.getId());	
						
			
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}	
		
	}
	
	private void testNotificationUpdate()	{
	
		try	{
			
			System.out.println(notification.DisplayInfo());
			
			event.setEventName("Quiz 2");
			event.setDate(formatter.parse("04/08/2019"));
			event.setType(EventType.ASSIGNMENT);
			event.setDescription("Second quiz of the semester!");
			
			eventDAO.save(event);
			
			notification.setEvent(event);
			notification.setChecked(true);
			notification.setDate(formatter.parse("04/01/2019"));
			notification.setNotificationName("New notification name");	
			notification.setIsNotified(true);
			
			
			
			// updates the notification
			notificationDAO.save(notification);
			
			// checks if the data has been updated by retrieving the notification's data from the db
			// first compare the changes made to the Event object within the Notification
			assertTrue(notificationDAO.getNotification(notification.getId()).getEvent().getEventName().equals("Quiz 2"));
			assertTrue(notificationDAO.getNotification(notification.getId()).getEvent().getDescription().equals("Second quiz of the semester!"));
			assertTrue(notificationDAO.getNotification(notification.getId()).getEvent().getDateStr().equals("04/08/2019 00:00"));
			assertTrue(notificationDAO.getNotification(notification.getId()).getEvent().getType() == EventType.ASSIGNMENT);	
			
			// then compare the changes made to the Notification's remaining attributes
			assertTrue(notification.getEvent() == event);
			assertTrue(notification.getEvent().getCourse() == course);
			assertTrue(notification.getEvent().getCourse().getUser() == user);
			assertTrue(notification.getIsNotified() == true);
			assertTrue(notification.getNotificationName().equals("New notification name"));			
			
			System.out.println(notification.DisplayInfo());
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}
	}
	
	private void testNotificationDelete()	{
		try	{
			
			notificationDAO.deleteNotification(notification.getId());
			
			// gets a list of all courses (there should be none as the DB is reset at every test and the only courses created is has been deleted)
			List<Notification> notifications = notificationDAO.getAllNotifications(user);
			
			// checks if the list is empty, and therefore the course has been successfully deleted
			assertTrue(notifications.isEmpty());		
						
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}
		
	}
	
	
	

}
