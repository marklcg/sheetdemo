package com.lcg.jsfdemo.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersistenceProducer {

	/**
	 * The JPA persistence manager
	 */
	@PersistenceContext
	@Produces
	EntityManager em;

}
