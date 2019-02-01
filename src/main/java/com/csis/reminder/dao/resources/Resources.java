package com.csis.reminder.dao.resources;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class responsible for providing EntityManager objects for DAO layer
 */
public class Resources {

	private static EntityManagerFactory factory = null;

	static {
		try {
			factory = Persistence.createEntityManagerFactory("pu");
		} catch (Exception e) {
			System.out.println("ERROR INITIALIZING FACTORY JPA");
			e.printStackTrace();
		}
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}