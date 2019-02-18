package com.csis.reminder.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Reminder Group
 * Class responsible for storing user attributes and database configuration for user table
 *
 */
@Entity
@Table
public class Event {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// References the courseID - should be later replaced by creating a CourseEvent entity
	private Course course;	
	private String eventName;
	private String description;
	private String date; // Maybe change to Date java.sql? 
	private String time; // Maybe change to Time java.sql? 
	
	// getters and setters
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
		
	
}
