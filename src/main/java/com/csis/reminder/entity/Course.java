package com.csis.reminder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table
public class Course
{
	
	/**
	 * @author Reminder Group
	 * Class responsible for storing user attributes and database configuration for course table
	 *
	 */

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;

		private String courseName;
		private String description;
		private String educationalInstitution ;
		private String courseInstructor;
		private Date  startDate;
		private Date  endDate;
		
		// GETS and SETS
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		
		public String getCourseName()
		{
			return courseName;
		}

		public void setCourseName(String courseName)
		{
			this.courseName = courseName;
		}

		public String getDescription()
		{
			return description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public Date getStartDate()
		{
			return startDate;
		}

		public void setStartDate(Date startDate)
		{
			this.startDate = startDate;
		}

		public Date getEndDate()
		{
			return endDate;
		}

		public void setEndDate(Date endDate)
		{
			this.endDate = endDate;
		}
		public String getEducationalInstitution()
		{
			return educationalInstitution;
		}

		public void setEducationalInstitution(String educationalInstitution)
		{
			this.educationalInstitution = educationalInstitution;
		}

		public String getCourseInstructor()
		{
			return courseInstructor;
		}

		public void setCourseInstructor(String courseInstructor)
		{
			this.courseInstructor = courseInstructor;
		}
	

	}

	

