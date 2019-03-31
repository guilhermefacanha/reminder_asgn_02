package com.csis.reminder.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.User;
import com.csis.reminder.util.ScreenUtil;

class CourseStateTest {

	User user;
	Course course;
	DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_FORMAT);
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
		user.setId(2);
		user.setEmail("test1@test.com");
		user.setEnabled(true);
		user.setLastName("Doe");
		user.setFirstName("John");
		user.setPassword("123");
		
		course = new Course();
		course.setId(1);
		course.setUser(user);
		course.setCourseInstructor("Instructor Test");
		course.setCourseName("Course Name Test");
		// Start Date - January 2nd, 2019
		course.setStartDate(formatter.parse("01/02/2019"));
		// End Date - April 30th, 2019
		course.setEndDate(formatter.parse("04/30/2019"));
		
	}

	@Test
	void testCourseGetSet() {
		assertTrue(course.getId() == 1);
		assertTrue(course.getUser() == user);
		assertTrue(course.getCourseInstructor().equals("Instructor Test"));
		assertTrue(course.getCourseName().equals("Course Name Test"));
		System.out.println(course.getStartDateStr());
		System.out.println(course.getEndDateStr());
		assertTrue(course.getStartDateStr().equals("01/02/2019"));
		assertTrue(course.getEndDateStr().equals("04/30/2019"));
		
	}

}
