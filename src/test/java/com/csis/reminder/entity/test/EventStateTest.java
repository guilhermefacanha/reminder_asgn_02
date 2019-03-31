package com.csis.reminder.entity.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.User;
import com.csis.reminder.entity.enumeration.EventType;
import com.csis.reminder.util.ScreenUtil;

class EventStateTest {

	User user;
	Course course;
	Event event;
	DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_FORMAT);
	
	
	@BeforeEach
	void setUp() throws Exception {
		// creates dummy user
		user = new User();
		user.setId(1);
		user.setEmail("test1@test.com");
		user.setEnabled(true);
		user.setLastName("Doe");
		user.setFirstName("John");
		user.setPassword("123");
		
		// creates dummy course
		course = new Course();
		course.setId(1);
		course.setUser(user);
		course.setCourseInstructor("Instructor Test");
		course.setCourseName("Course Name Test");
		// Start Date - January 2nd, 2019
		course.setStartDate(formatter.parse("01/02/2019"));
		// End Date - April 30th, 2019
		course.setEndDate(formatter.parse("04/30/2019"));
		
		//creates dummy event
		event = new Event();
		event.setId(1);
		event.setCourse(course);
		event.setEventName("Quiz 1");
		event.setDate(formatter.parse("03/25/2019"));
		event.setType(EventType.QUIZ);
		event.setDescription("First quiz of the semester!");		
	}

	@Test
	void testEventGetSet() {
		
		assertTrue(event.getId() == 1);
		assertTrue(event.getCourse() == course);
		assertTrue(event.getEventName().equals("Quiz 1"));
		assertTrue(event.getDescription().equals("First quiz of the semester!"));
		assertTrue(event.getDateStr().equals("03/25/2019 00:00"));
		assertTrue(event.getType() == EventType.QUIZ);
		
	}

}
