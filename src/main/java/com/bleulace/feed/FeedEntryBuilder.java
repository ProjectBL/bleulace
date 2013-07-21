package com.bleulace.feed;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;
import com.bleulace.utils.jpa.EntityManagerReference;

@Configurable
public class FeedEntryBuilder
{
	private Set<Account> accounts = new HashSet<Account>();

	private Map<String, Serializable> payloads = new HashMap<String, Serializable>();

	public FeedEntryBuilder()
	{
	}

	@PersistenceContext
	private EntityManager em;

	public FeedEntryBuilder addAccounts(Account... accounts)
	{
		Assert.noNullElements(accounts);
		this.accounts.addAll(Arrays.asList(accounts));
		return this;
	}

	public FeedEntryBuilder addAccounts(String... accountIds)
	{
		Assert.noNullElements(accountIds);
		for (String id : accountIds)
		{
			accounts.add(EntityManagerReference.load(Account.class, id));
		}
		return this;
	}

	public FeedEntryBuilder addAccounts(Collection<Account> accounts)
	{
		return addAccounts(new HashSet<Account>(accounts)
				.toArray(new Account[accounts.size()]));
	}

	public FeedEntryBuilder addFriends(Account... accounts)
	{
		for (Account account : accounts)
		{
			addAccounts(account.getFriends());
		}
		return this;
	}

	public FeedEntryBuilder addFriends(String... accountIds)
	{
		for (String id : accountIds)
		{
			addFriends(EntityManagerReference.load(Account.class, id));
		}
		return this;
	}

	public FeedEntryBuilder addWithFriends(Collection<Account> accounts)
	{
		return addWithFriends(new HashSet<Account>(accounts)
				.toArray(new Account[accounts.size()]));
	}

	public FeedEntryBuilder addWithFriends(Account... accounts)
	{
		for (Account account : accounts)
		{
			addAccounts(account);
			addAccounts(account.getFriends());
		}
		return this;
	}

	public FeedEntryBuilder addWithFriends(String... accounts)
	{
		for (String id : accounts)
		{
			Account account = EntityManagerReference.load(Account.class, id);
			addAccounts(account);
			addAccounts(account.getFriends());
		}
		return this;
	}

	public FeedEntryBuilder addPayloads(Serializable... data)
	{
		Assert.noNullElements(data);
		for (Serializable s : data)
		{
			payloads.put(s.getClass().getSimpleName(), s);
		}
		return this;
	}

	public FeedEntryBuilder build()
	{
		for (Account account : accounts)
		{
			em.persist(new FeedEntry(account, payloads));
		}
		accounts.clear();

		return this;
	}
}