package com.bleulace.crm.infrastructure;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;

/**
 * A subclass of {@link WildcardPermission} and {@link DomainPermission}
 * configured for persistent storage via JPA.
 * 
 * @author Arleigh Dickerson
 * @see Permission
 * 
 */
@Entity
public class JpaPermission extends DomainPermission implements
		Persistable<String>
{
	private static final long serialVersionUID = 6349430428412117339L;

	private String id;
	private Account account;

	@SuppressWarnings("unused")
	private JpaPermission()
	{
	}

	protected JpaPermission(Account account, String domain,
			Set<String> actions, Set<String> targets)
	{
		Assert.noNullElements(new Object[] { account, domain, actions, targets });
		this.account = account;
		setParts(domain, actions, targets);
	}

	@Override
	@Id
	@GeneratedValue(generator = "system-uuid")
	public String getId()
	{
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(String id)
	{
		this.id = id;
	}

	@JoinColumn(nullable = false, updatable = false)
	@ManyToOne
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
		return id == null;
	}
}