package com.bleulace.domain.authz;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.shiro.authz.permission.DomainPermission;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import com.bleulace.domain.account.Account;

@Entity
public class JPAPermission extends DomainPermission implements
		Persistable<Long>
{
	private static final long serialVersionUID = 6349430428412117339L;

	private Long id;

	private Account account;

	@SuppressWarnings("unused")
	private JPAPermission()
	{
	}

	protected JPAPermission(Account account, String domain,
			Set<String> actions, Set<String> targets)
	{
		Assert.noNullElements(new Object[] { account, domain, actions, targets });
		Assert.isTrue(!account.isNew());
		setParts(domain, actions, targets);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId()
	{
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id)
	{
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(updatable = false, nullable = false)
	public Account getAccount()
	{
		return account;
	}

	@SuppressWarnings("unused")
	private void setAccount(Account account)
	{
		this.account = account;
	}

	@Basic
	@Override
	public String getDomain()
	{
		return super.getDomain();
	}

	@Override
	@ElementCollection
	public Set<String> getActions()
	{
		return super.getActions();
	}

	@Override
	@ElementCollection
	public Set<String> getTargets()
	{
		return super.getTargets();
	}

	@Transient
	@Override
	public boolean isNew()
	{
		return id != null;
	}
}