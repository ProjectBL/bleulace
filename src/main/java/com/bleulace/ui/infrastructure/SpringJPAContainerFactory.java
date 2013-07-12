package com.bleulace.ui.infrastructure;

import com.vaadin.addon.jpacontainer.JPAContainer;

public class SpringJPAContainerFactory
{
	private SpringJPAContainerFactory()
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
