package com.bleulace.utils;

import com.vaadin.addon.jpacontainer.JPAContainer;

public class BLJPAContainerFactory
{
	private BLJPAContainerFactory()
	{
	}

	public static <T> JPAContainer<T> make(Class<T> entityClass)
	{
		JPAContainer<T> container = new JPAContainer<T>(entityClass);
		container.setEntityProvider(new TransactionalEntityProvider<T>(
				entityClass));
		return container;
	}
}
