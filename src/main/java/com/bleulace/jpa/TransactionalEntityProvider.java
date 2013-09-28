package com.bleulace.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

@Configurable(preConstruction = true)
class TransactionalEntityProvider<T> extends MutableLocalEntityProvider<T>
{
	@PersistenceContext
	private EntityManager em;

	public TransactionalEntityProvider(Class<T> entityClass)
	{
		super(entityClass);
		setEntityManager(em);
		setTransactionsHandledByProvider(false);
		setEntitiesDetached(false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	protected void runInTransaction(Runnable operation)
	{
		super.runInTransaction(operation);
	}
}