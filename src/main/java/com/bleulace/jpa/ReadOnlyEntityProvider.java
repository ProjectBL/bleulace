package com.bleulace.jpa;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.provider.LocalEntityProvider;

public class ReadOnlyEntityProvider<T> extends LocalEntityProvider<T>
{
	public ReadOnlyEntityProvider(Class<T> entityClass, EntityManager em)
	{
		super(entityClass);
		setEntitiesDetached(false);
		setEntityManager(em);
	}
}