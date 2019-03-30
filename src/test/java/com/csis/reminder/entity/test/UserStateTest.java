package com.csis.reminder.entity.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.csis.reminder.entity.User;

public class UserStateTest {

	User user;

	@BeforeEach
	void setup() throws Exception {
		user = new User();
		user.setId(1);
		user.setEmail("admin@email.com");
		user.setEnabled(true);
		user.setLastName("Doe");
		user.setFirstName("John");
		user.setPassword("123");
	}
	
	@Test
	public void testUserGetSet() {
		assertTrue(user.getId() == 1);
		assertTrue(user.getEmail().equals("admin@email.com"));
		assertTrue(user.isEnabled() == true);
		assertTrue(user.getLastName().equals("Doe"));
		assertTrue(user.getFirstName().equals("John"));
		assertTrue(user.getPassword().equals("123"));
	}
	
	@Test
	public void testUserEnabled() {
		user.setEnabled(false);
		assertTrue(user.isEnabled() == false);
	}

}
