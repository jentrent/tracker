package com.jentrent.tracker.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDAO{

	private static EntityManagerFactory emf;

	private EntityManager em;

	static{
		emf = Persistence.createEntityManagerFactory("tracker_pu");
	}

	public BaseDAO(){

	}

	protected EntityManager getEm(){

		if(em == null){
			em = emf.createEntityManager();
		}

		return em;
	}

	protected void beginTrx(){

		if(!getEm().getTransaction().isActive()){
			getEm().getTransaction().begin();
		}

	}

	protected void commitTrx(){

		if(getEm().getTransaction().isActive()){
			getEm().getTransaction().commit();
		}

	}

}
