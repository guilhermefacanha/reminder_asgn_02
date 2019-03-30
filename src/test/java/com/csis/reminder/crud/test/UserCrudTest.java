package com.csis.reminder.crud.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.csis.reminder.dao.UserDAO;
import com.csis.reminder.entity.User;

@TestInstance(Lifecycle.PER_CLASS)
public class UserCrudTest {
	User user;
	UserDAO dao;
	
	@BeforeAll
	void setupDao() {
		dao = new UserDAO();
	}

	@BeforeEach
	void setup() throws Exception {
		user = new User();
		System.out.println("User object created!!");
	}
	
	@Test
	public void testUserCrud() {
		testUserCreate();
		testUserGetByEmail();
		testUserGet();
	}
	
	private void testUserCreate() {
		user.setEmail("admin@email.com");
		user.setEnabled(true);
		user.setLastName("Doe");
		user.setFirstName("John");
		user.setPassword("123");
		
		dao.saveUser(user);

		assertTrue(user.getId() == 1);
	}
	
	private void testUserGetByEmail() {
		assertTrue(dao.emailExists("admin@email.com"));
	}
	
	private void testUserGet() {
		user = dao.getUser(1);
		assertTrue(user.getId() == 1);
		assertTrue(user.getEmail().equals("admin@email.com"));
		assertTrue(user.isEnabled() == true);
		assertTrue(user.getLastName().equals("Doe"));
		assertTrue(user.getFirstName().equals("John"));
		assertTrue(user.getPassword().equals("123"));
	}
}
