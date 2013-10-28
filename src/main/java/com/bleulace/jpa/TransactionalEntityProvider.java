package com.bleulace.jpa;

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
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager)
	{
		super.setEntityManager(entityManager);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	protected void runInTransaction(Runnable operation)
	{
		super.runInTransaction(operation);
	}
}