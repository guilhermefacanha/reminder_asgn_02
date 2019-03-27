package com.csis.reminder.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.csis.reminder.util.ScreenUtil;

/**
 * @author Reminder Group Class responsible for storing notification attributes
 *
 */
@Entity
@Table
public class Notification
{

	   @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;

		@ManyToOne
		private Event event;

		@Column(nullable = false)
		private String notificationName;
		
		@Column(nullable = false)
		private Date date;

		public String getDateStr() {
			try {
				return new SimpleDateFormat(ScreenUtil.DATE_TIME_FORMAT).format(date);
			} catch (Exception e) {
				return "";
			}
		}

		public Object[] getData() {
			return new Object[] { id, event.getEventName() , notificationName, getDateStr() };
		}

		// getters and setters
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Event getEvent() {
			return event;
					
		}
		
		public void setEvent(Event event) {
			this.event = event;
		}


		public String getNotificationName() {
			return notificationName;
		}

		public void setNotificationName(String notificationName) {
			this.notificationName = notificationName;
		}
		
		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	
	
	
}
