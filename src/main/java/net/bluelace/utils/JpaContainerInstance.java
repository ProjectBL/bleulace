package net.bluelace.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

@Configurable(preConstruction = true)
public class JpaContainerInstance
{
	@PersistenceContext
	private EntityManager entityManager;

	private JpaContainerInstance()
	{
	}

	public static <T> JPAContainer<T> create(Class<T> entityClass)
	{
		return JPAContainerFactory.make(entityClass,
				new JpaContainerInstance().entityManager);
	}
}
