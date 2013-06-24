package com.bleulace.domain;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.account.Account;

@Profile({ "dev", "prod" })
@Component
public class DatabasePopulator implements
		ApplicationListener<ContextRefreshedEvent>
{
	@Autowired
	@Qualifier("mockAccountIterator")
	private Iterator<Account> mockAccountIterator;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		if (Account.findAll().size() > 10)
		{
			return;
		}
		while (mockAccountIterator.hasNext())
		{
			entityManager.persist(mockAccountIterator.next());
		}
		entityManager.flush();
	}
}