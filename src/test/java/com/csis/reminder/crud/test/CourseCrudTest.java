package com.csis.reminder.crud.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.csis.reminder.dao.CourseDAO;
import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.User;
import com.csis.reminder.util.ScreenUtil;

@TestInstance(Lifecycle.PER_CLASS)
class CourseCrudTest {

	User user;
	UserDAO userDAO;
	Course course;
	CourseDAO courseDAO;
	DateFormat formatter = new SimpleDateFormat(ScreenUtil.DATE_FORMAT);
	
	@BeforeAll
	void setupDAOs()	{
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
		
	}	
	
	@Test
	public void testCourseCrud() {
		testCourseCreate();
		testCourseUpdate();
		testCourseDelete();
		// All three methods already contain a select, so there is no need to re-test it
	}

	
	private void testCourseCreate() {
		try	{
			// sets random values to the dummy course
			course.setId(1);
			course.setUser(user);
			course.setCourseInstructor("Instructor Test");
			course.setCourseName("Course Name Test");
			// start date - January 2nd, 2019
			course.setStartDate(formatter.parse("01/02/2019"));
			// end date - April 30th, 2019
			course.setEndDate(formatter.parse("04/30/2019"));
			
			courseDAO.saveCourse(course);
				
			// checks if the course has been stored in the db by comparing the object's data with data retrieved from the db
			assertTrue(courseDAO.getCourse(1).getId() == course.getId());
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}		
	}
	
	private void testCourseUpdate()	{
		try	{
		
			course.setCourseName("Updated Course Name");
			course.setCourseInstructor("Updated Instructor Name");
			course.setStartDate(formatter.parse("05/01/2019"));
			course.setEndDate(formatter.parse("08/30/2019"));
			
			// updates the course
			courseDAO.saveCourse(course);
			
			// checks if the data has been updated by retrieving the course's data from the db
			assertTrue(courseDAO.getCourse(course.getId()).getCourseName().equals("Updated Course Name"));
			assertTrue(courseDAO.getCourse(course.getId()).getCourseInstructor().equals("Updated Instructor Name"));
			assertTrue(courseDAO.getCourse(course.getId()).getStartDateStr().equals("05/01/2019"));
			assertTrue(courseDAO.getCourse(course.getId()).getEndDateStr().equals("08/30/2019"));
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}
	}
	
	private void testCourseDelete()	{
		try	{
			courseDAO.deleteCourse(course.getId());
			
			// gets a list of all courses (there should be none as the DB is reset at every test and the only courses created is has been deleted)
			List<Course> courses = courseDAO.getAllCourses(user);
			
			// checks if the list is empty, and therefore the course has been successfully deleted
			assertTrue(courses.isEmpty());		
						
		}
		catch (Exception ex) {
			System.out.println(ex.getStackTrace() + " " + ex.getMessage());
		}
		
	}

}
