package com.bleulace.crm.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.persistence.infrastructure.QueryFactory;

/**
 * An implementation of {@link AccountFinder} which queries persistent
 * {@link Account} instances.
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component("accountFinder")
@Transactional(readOnly = true)
public class AccountFinderImpl implements AccountFinder
{
	private static final QAccount QACCOUNT = QAccount.account;

	@Override
	public Account findById(String id)
	{
		return QueryFactory.from(QACCOUNT).where(QACCOUNT.id.eq(id))
				.uniqueResult(QACCOUNT);
	}

	@Override
	public Account findByEmail(String email)
	{
		return QueryFactory.from(QACCOUNT)
				.where(QACCOUNT.email.equalsIgnoreCase(email))
				.uniqueResult(QACCOUNT);

	}
}