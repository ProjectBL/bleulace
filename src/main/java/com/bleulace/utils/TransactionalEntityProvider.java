package com.bleulace.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

@Configurable(preConstruction = true)
public class TransactionalEntityProvider<T> extends
		MutableLocalEntityProvider<T>
{
	public TransactionalEntityProvider(Class<T> entityClass)
	{
		super(entityClass);
		setTransactionsHandledByProvider(false);
		setEntitiesDetached(false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	protected void runInTransaction(Runnable operation)
	{
		super.runInTransaction(operation);
	}

	@Override
	public T updateEntity(final T entity)
	{
		return super.updateEntity(entity);
	}

	@Override
	public T addEntity(final T entity)
	{
		return super.addEntity(entity);
	}

	@Override
	public void removeEntity(final Object entityId)
	{
		super.removeEntity(entityId);
	}

	@Override
	public void updateEntityProperty(final Object entityId,
			final String propertyName, final Object propertyValue)
			throws IllegalArgumentException
	{
		super.updateEntityProperty(entityId, propertyName, propertyValue);
	}

	@Override
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager)
	{
		super.setEntityManager(entityManager);
	}
}