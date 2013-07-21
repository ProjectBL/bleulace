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
public class NewsFeedEnvelope
{
	private Set<Account> accounts = new HashSet<Account>();

	private Map<String, Serializable> payloads = new HashMap<String, Serializable>();

	public NewsFeedEnvelope()
	{
	}

	@PersistenceContext
	private EntityManager em;

	public NewsFeedEnvelope addAccounts(Account... accounts)
	{
		Assert.noNullElements(accounts);
		this.accounts.addAll(Arrays.asList(accounts));
		return this;
	}

	public NewsFeedEnvelope addAccounts(String... accountIds)
	{
		Assert.noNullElements(accountIds);
		for (String id : accountIds)
		{
			accounts.add(EntityManagerReference.load(Account.class, id));
		}
		return this;
	}

	public NewsFeedEnvelope addAccounts(Collection<Account> accounts)
	{
		return addAccounts(new HashSet<Account>(accounts)
				.toArray(new Account[accounts.size()]));
	}

	public NewsFeedEnvelope addFriends(Account... accounts)
	{
		for (Account account : accounts)
		{
			addAccounts(account.getFriends());
		}
		return this;
	}

	public NewsFeedEnvelope addFriends(String... accountIds)
	{
		for (String id : accountIds)
		{
			addFriends(EntityManagerReference.load(Account.class, id));
		}
		return this;
	}

	public NewsFeedEnvelope addWithFriends(Collection<Account> accounts)
	{
		return addWithFriends(new HashSet<Account>(accounts)
				.toArray(new Account[accounts.size()]));
	}

	public NewsFeedEnvelope addWithFriends(Account... accounts)
	{
		for (Account account : accounts)
		{
			addAccounts(account);
			addAccounts(account.getFriends());
		}
		return this;
	}

	public NewsFeedEnvelope addWithFriends(String... accounts)
	{
		for (String id : accounts)
		{
			Account account = EntityManagerReference.load(Account.class, id);
			addAccounts(account);
			addAccounts(account.getFriends());
		}
		return this;
	}

	public NewsFeedEnvelope withPayloads(Serializable... data)
	{
		Assert.noNullElements(data);
		for (Serializable s : data)
		{
			payloads.put(s.getClass().getSimpleName(), s);
		}
		return this;
	}

	public NewsFeedEnvelope send()
	{
		for (Account account : accounts)
		{
			em.persist(new FeedEntry(account, payloads));
		}
		accounts.clear();

		return this;
	}
}