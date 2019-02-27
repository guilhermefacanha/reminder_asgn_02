package com.csis.reminder.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Course {

	/**
	 * @author Reminder Group Class responsible for storing user attributes and
	 *         database configuration for course table
	 *
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User user;
	private String courseName;
	private String courseInstructor;
	private Date startDate;
	private Date endDate;

	public String getStartDateStr() {
		try {
			return startDate.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public String getEndDateStr() {
		try {
			return endDate.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public Object[] getData() {
		Object[] data = { id, courseName, courseInstructor, getStartDateStr(), getEndDateStr() };
		return data;
	}
	
	// getters and setters
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCourseInstructor() {
			return courseInstructor;
		}

		public void setCourseInstructor(String courseInstructor) {
			this.courseInstructor = courseInstructor;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

}
