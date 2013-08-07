package com.bleulace.crm.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bleulace.persistence.infrastructure.QueryFactory;

/**
 * An implementation of {@link AccountDAOCustom} which queries persistent
 * {@link Account} instances.
 * 
 * @author Arleigh Dickerson
 * 
 */
class AccountDAOImpl implements AccountDAOCustom
{
	private static final QAccount QACCOUNT = QAccount.account;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Account findByEmail(String email)
	{
		return QueryFactory.from(QACCOUNT)
				.where(QACCOUNT.email.equalsIgnoreCase(email))
				.uniqueResult(QACCOUNT);
	}

	@Override
	public boolean areFriends(String id1, String id2)
	{
		//@formatter:off
		Query query = entityManager.createQuery(
				 "SELECT COUNT(a1) "
			+    "FROM Account a1 "
			+    "JOIN a1.friends a2 "
			+    "WHERE a1.id=:id1 "
			+    "AND a2.id=:id2");
		//@formatter:on
		query.setParameter("id1", id1);
		query.setParameter("id2", id2);
		return ((Long) query.getSingleResult()) > 0;
	}
}