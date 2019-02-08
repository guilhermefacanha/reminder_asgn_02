package com.csis.reminder.session;

import com.csis.reminder.entity.User;

/**
 * @author Group Reminder
 * Class responsible for storing static memory with the logged user
 */
public class UserSession {
	private static User user;
	
	/**
	 * Retrieve the user stored in memory
	 * @return {@link User} memory stored user
	 */
	public static User getUser() {
		return user;
	}
	
	/**
	 * Method to store a user object in static memory
	 * @param user {@link User}
	 */
	public static void setUser(User user) {
		UserSession.user = user;
	}
}
