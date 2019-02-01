package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.User;

public class UserDAO implements Serializable {

	private static final long serialVersionUID = 5584599198950863626L;

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		EntityManager manager = Resources.getEntityManager();
		List<User> users = manager.createQuery("SELECT x FROM User x").getResultList();
		return users;
	}
}
