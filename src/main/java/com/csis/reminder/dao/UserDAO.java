package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.User;
import com.csis.reminder.util.MD5Util;

public class UserDAO implements Serializable {

	private static final long serialVersionUID = 5584599198950863626L;
	
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
	
	public void addUser(User user) {
		EntityManager manager = Resources.getEntityManager();
		manager.persist(user);
	}

	public boolean verifyLogin(String email, String password) {
		EntityManager manager = Resources.getEntityManager();
		try {
			User user = (User) manager.createQuery("SELECT x FROM User x WHERE x.email = :email").setParameter("email", email).getSingleResult();
			
			String hashPass = MD5Util.getMd5(password);
			if(user.getPassword().equals(hashPass))
				return true;
			
		} catch (NoResultException e) {
			return false;
		}
		return false;
	}
}
