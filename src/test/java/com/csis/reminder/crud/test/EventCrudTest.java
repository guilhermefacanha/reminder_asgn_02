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
import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.EventType;
import com.csis.reminder.util.ScreenUtil;

@TestInstance(Lifecycle.PER_CLASS)
class EventCrudTest {

	User user;
	Course course;
	Event event;
	UserDAO userDAO;
	CourseDAO courseDAO;
	EventDAO eventDAO;
	DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_FORMAT);
	
	
	@BeforeAll
	void setupDAOs()	{
		eventDAO = new EventDAO();
		userDAO = new UserDAO();
		courseDAO = new CourseDAO();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
		System.out.println("User object created!!");
		userDAO.saveUser(user);
		System.out.println("User stored in db!!");
		course = new Course();
		System.out.println("Course object created!!");
		courseDAO.saveCourse(course);
		System.out.println("Course stored in db!!");		
	}
	
	

	@Test
	void testEventCrud() {
		testEventCreate();
		testEventUpdate();
		testEventDelete();
	}
	
	private void testEventCreate() {
		try	{
			
			//creates dummy event
			event = new Event();
			event.setId(1);
			event.setCourse(course);
			event.setEventName("Quiz 1");
			event.setDate(formatter.parse("03/25/2019"));
			event.setType(EventType.QUIZ);
			event.setDescription("First quiz of the semester!");
			
			eventDAO.save(event);
			
			// checks if the event has been stored in the db by comparing the object's data with data retrieved from the db
			assertTrue(eventDAO.getEvent(1).getId() == event.getId());			
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}	
	}
	
	private void testEventUpdate()	{
		try	{
		
			course.setCourseName("Updated Course Name");
			course.setCourseInstructor("Updated Instructor Name");
			course.setStartDate(formatter.parse("05/01/2019"));
			course.setEndDate(formatter.parse("08/30/2019"));
			
			event.setCourse(course);
			event.setEventName("Quiz 2");
			event.setDate(formatter.parse("04/08/2019"));
			event.setType(EventType.ASSIGNMENT);
			event.setDescription("Second quiz of the semester!");
			
			// updates the event
			eventDAO.save(event);
			
			// checks if the data has been updated by retrieving the event's data from the db
			assertTrue(eventDAO.getEvent(event.getId()).getCourse().getCourseName().equals("Updated Course Name"));
			assertTrue(eventDAO.getEvent(event.getId()).getCourse().getCourseInstructor().equals("Updated Instructor Name"));
			assertTrue(eventDAO.getEvent(event.getId()).getCourse().getStartDateStr().equals("05/01/2019"));
			assertTrue(eventDAO.getEvent(event.getId()).getCourse().getEndDateStr().equals("08/30/2019"));
			
			assertTrue(eventDAO.getEvent(event.getId()).getEventName().equals("Quiz 2"));
			assertTrue(eventDAO.getEvent(event.getId()).getDescription().equals("Second quiz of the semester!"));
			assertTrue(eventDAO.getEvent(event.getId()).getDateStr().equals("04/08/2019 00:00"));
			assertTrue(eventDAO.getEvent(event.getId()).getType() == EventType.ASSIGNMENT);				
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}
	}
	
	private void testEventDelete()	{
		try	{
			
			eventDAO.deleteEvent(event.getId());
			
			// gets a list of all courses (there should be none as the DB is reset at every test and the only courses created is has been deleted)
			List<Event> events = eventDAO.getAllEvents(user);
			
			// checks if the list is empty, and therefore the course has been successfully deleted
			assertTrue(events.isEmpty());		
						
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}
		
	}
	
	

}
