package com.bleulace.ui.infrastructure;

import com.vaadin.addon.jpacontainer.JPAContainer;

/**
 * Creates a Vaadin JPAContainer instance with transactions managed by the
 * spring container.
 * 
 * @author Arleigh Dickerson
 * 
 */
public class SpringJPAContainerFactory
{
	// static calls only
	private SpringJPAContainerFactory()
	{
	}

	/**
	 * 
	 * @param entityClass
	 *            targeted entity
	 * @return JPAContainer instance supporting mutable transactions and ready
	 *         for use.
	 */
	public static <T> JPAContainer<T> make(Class<T> entityClass)
	{
		JPAContainer<T> container = new JPAContainer<T>(entityClass);
		container.setEntityProvider(new TransactionalEntityProvider<T>(
				entityClass));
		return container;
	}
}
