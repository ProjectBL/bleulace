package com.bleulace.crm.domain;

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

	@Override
	public Account findByEmail(String email)
	{
		return QueryFactory.from(QACCOUNT)
				.where(QACCOUNT.email.equalsIgnoreCase(email))
				.uniqueResult(QACCOUNT);
	}
}