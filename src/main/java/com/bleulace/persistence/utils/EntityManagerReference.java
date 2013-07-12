package com.bleulace.persistence.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class EntityManagerReference
{
	@PersistenceContext
	private EntityManager entityManager;

	private EntityManagerReference()
	{
	}

	public static EntityManager get()
	{
		return new EntityManagerReference().entityManager;
	}
}