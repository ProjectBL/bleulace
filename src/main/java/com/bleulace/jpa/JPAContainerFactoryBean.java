package com.bleulace.jpa;

import org.springframework.beans.factory.FactoryBean;

import com.bleulace.web.demo.calendar.ViewTargetChangedEvent;
import com.google.common.eventbus.Subscribe;
import com.vaadin.addon.jpacontainer.JPAContainer;

public class JPAContainerFactoryBean<T> extends JPAContainer<T> implements
		FactoryBean<JPAContainer<T>>
{
	public JPAContainerFactoryBean(Class<T> entityClass)
	{
		super(entityClass);
	}

	@Override
	public JPAContainer<T> getObject() throws Exception
	{
		return this;
	}

	@Override
	public Class<?> getObjectType()
	{
		return JPAContainer.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	@Subscribe
	public void subscribe(ViewTargetChangedEvent event)
	{
		applyFilters();
	}
}