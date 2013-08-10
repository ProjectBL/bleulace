package com.bleulace.domain.crm.model;

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

import com.bleulace.utils.jpa.EntityManagerReference;

/**
 * 
 * Builder for adding entries to newsfeeds
 * 
 * @author Arleigh Dickerson
 * 
 */
@Configurable
public class FeedEnvelope
{
	private Set<Account> accounts = new HashSet<Account>();

	private Map<String, Serializable> payloads = new HashMap<String, Serializable>();

	public FeedEnvelope()
	{
	}

	@PersistenceContext
	private EntityManager em;

	public FeedEnvelope addAccounts(Account... accounts)
	{
		Assert.noNullElements(accounts);
		this.accounts.addAll(Arrays.asList(accounts));
		return this;
	}

	public FeedEnvelope addAccounts(String... accountIds)
	{
		Assert.noNullElements(accountIds);
		for (String id : accountIds)
		{
			accounts.add(EntityManagerReference.load(Account.class, id));
		}
		return this;
	}

	public FeedEnvelope addAccounts(Collection<Account> accounts)
	{
		return addAccounts(new HashSet<Account>(accounts)
				.toArray(new Account[accounts.size()]));
	}

	public FeedEnvelope addFriends(Account... accounts)
	{
		for (Account account : accounts)
		{
			addAccounts(account.getFriends());
		}
		return this;
	}

	public FeedEnvelope addFriends(String... accountIds)
	{
		for (String id : accountIds)
		{
			addFriends(EntityManagerReference.load(Account.class, id));
		}
		return this;
	}

	public FeedEnvelope addWithFriends(Collection<Account> accounts)
	{
		return addWithFriends(new HashSet<Account>(accounts)
				.toArray(new Account[accounts.size()]));
	}

	public FeedEnvelope addWithFriends(Account... accounts)
	{
		for (Account account : accounts)
		{
			addAccounts(account);
			addAccounts(account.getFriends());
		}
		return this;
	}

	public FeedEnvelope addWithFriends(String... accounts)
	{
		for (String id : accounts)
		{
			Account account = EntityManagerReference.load(
					Account.class, id);
			addAccounts(account);
			addAccounts(account.getFriends());
		}
		return this;
	}

	public FeedEnvelope withPayloads(Serializable... data)
	{
		Assert.noNullElements(data);
		for (Serializable s : data)
		{
			payloads.put(s.getClass().getSimpleName(), s);
		}
		return this;
	}

	public FeedEnvelope send()
	{
		for (Account account : accounts)
		{
			em.persist(new FeedEntry(account, payloads));
		}
		accounts.clear();

		return this;
	}
}