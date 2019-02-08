package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.User;
import com.csis.reminder.session.UserSession;
import com.csis.reminder.util.MD5Util;

/**
 * 
 * @author Reminder Group
 * Class is responsible for managing the User Data Access Object
 * it contains the methods which allow us to perform CRUD operations on 
 * our users
 */
public class UserDAO implements Serializable {

	private static final long serialVersionUID = 5584599198950863626L;
	
	/**
	 * Method to run a first query to initialize the database connection context
	 */
	public void initSelec() {
		EntityManager manager = Resources.getEntityManager();
		manager.find(User.class, new Long(1));
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		EntityManager manager = Resources.getEntityManager();
		List<User> users = manager.createQuery("SELECT x FROM User x").getResultList();
		return users;
	}
	
	/**
	 * Method to insert (persist) a new user into our database
	 * @param user {@link User} - object which holds a user's data
	 * its information is gathered in the Register class
	 */	
	public void addUser(User user) {
		EntityManager manager = Resources.getEntityManager();
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();
		manager.persist(user);
		transaction.commit();
	}

	/**
	 * Method that gets the user input for email and password and check in database if the login is success.
	 * @param email - {@link String} user email
	 * @param password - {@link String} user password
	 * @return {@link Boolean} login success
	 */
	public boolean verifyLogin(String email, String password) {
		EntityManager manager = Resources.getEntityManager();
		try {
			User user = (User) manager.createQuery("SELECT x FROM User x WHERE x.email = :email").setParameter("email", email).getSingleResult();
			
			String hashPass = MD5Util.getMd5(password);
			if(user.getPassword().equals(hashPass)) {
				UserSession.setUser(user);
				return true;
			}
			
		} catch (NoResultException e) {
			return false;
		}
		return false;
	}

	/**
	 * Method to check if a user email is already registered in database
	 * @param email - {@link String} user email
	 * @return {@link Boolean} if email already exists on database
	 */
	public boolean emailExists(String email) {
		EntityManager manager = Resources.getEntityManager();
		try {
			manager.createQuery("SELECT x FROM User x WHERE x.email = :email").setParameter("email", email).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
