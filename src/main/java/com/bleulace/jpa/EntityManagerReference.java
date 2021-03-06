package com.bleulace.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * A thread-safe way to get a static reference to the EntityManager wherever one
 * pleases.
 * 
 * This is quick and dirty.
 * 
 * I should probably stop using this at some point, new() calls can get
 * expensive...
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configurable(preConstruction = true)
public class EntityManagerReference
{
	@PersistenceContext
	private EntityManager entityManager;

	private EntityManagerReference()
	{
	}

	public static <T> T load(Class<T> clazz, Object id)
	{
		return get().getReference(clazz, id);
	}

	public static EntityManager get()
	{
		return new EntityManagerReference().entityManager;
	}
}