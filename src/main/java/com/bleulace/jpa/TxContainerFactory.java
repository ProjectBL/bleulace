package com.bleulace.jpa;

import com.vaadin.addon.jpacontainer.JPAContainer;

//Transactional JPAContainer Factory
public abstract class TxContainerFactory
{
	private TxContainerFactory()
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