package com.bleulace.jpa;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

public class TransactionalEntityProvider<T> extends
		MutableLocalEntityProvider<T>
{
	public TransactionalEntityProvider(Class<T> entityClass, EntityManager em)
	{
		super(entityClass);
		setTransactionsHandledByProvider(false);
		setEntitiesDetached(false);
		setEntityManager(em);
	}

	@Override
	@Transactional
	protected void runInTransaction(Runnable operation)
	{
		super.runInTransaction(operation);
	}
}