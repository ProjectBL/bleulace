package com.bleulace.utils.ui;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

/**
 * I did not come up with this on my own, but I did make some changes to reduce
 * boilerplate code. These changes are dependent on an environment which weaves
 * spring aspects at build-time or class-load time.
 * 
 * Props to this guy: https://vaadin.com/forum#!/thread/867020
 * 
 * @author Arleigh Dickerson
 * 
 * @param <T>
 *            targeted entity.
 */
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