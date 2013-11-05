package com.bleulace.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.utils.IdCallback;
import com.bleulace.utils.IdsCallback;
import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;

@Component("jpaContainer")
@Scope("prototype")
class JPAContainerFactoryBean<T> implements FactoryBean<JPAContainer<T>>
{
	@PersistenceContext
	private EntityManager em;

	private IdsCallback callback;
	private final Class<T> entityClazz;
	private final Class<? extends EntityProvider<T>> providerClazz;

	@SuppressWarnings("unused")
	private JPAContainerFactoryBean()
	{
		this(null, null, (IdsCallback) null);
	}

	public JPAContainerFactoryBean(Class<T> entityClazz,
			Class<? extends EntityProvider<T>> providerClazz)
	{
		this(entityClazz, providerClazz, (IdsCallback) null);
	}

	public JPAContainerFactoryBean(Class<T> entityClazz,
			Class<? extends EntityProvider<T>> providerClazz,
			IdsCallback callback)
	{
		this.entityClazz = entityClazz;
		this.providerClazz = providerClazz;
		this.callback = callback;
	}

	public JPAContainerFactoryBean(Class<T> entityClazz,
			Class<? extends EntityProvider<T>> providerClazz,
			IdCallback callback)
	{
		this(entityClazz, providerClazz, callback.asIdsCallback());
	}

	@Override
	public JPAContainer<T> getObject() throws Exception
	{
		JPAContainer<T> container = new JPAContainer<T>(entityClazz);
		EntityProvider<T> provider = ConstructorUtils.invokeConstructor(
				providerClazz, entityClazz, em);
		container.setEntityProvider(provider);
		if (callback != null)
		{
			container.setQueryModifierDelegate(new IdCollectionQueryModifier(
					callback));
		}
		return container;
	}

	@Override
	public Class<?> getObjectType()
	{
		return JPAContainer.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}