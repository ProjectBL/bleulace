package com.bleulace.domain.crm.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

/**
 * A subclass of {@link WildcardPermission} and {@link DomainPermission}
 * configured for persistent storage via JPA.
 * 
 * We are using annotatings on METHODS instead of FIELDS for JPA metadata
 * because this class extends an unannotated base class.
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

	@SuppressWarnings("unused")
	private JpaPermission()
	{
	}

	protected JpaPermission(String domain, Set<String> actions,
			Set<String> targets)
	{
		Assert.noNullElements(new Object[] { domain, actions, targets });
		Assert.noNullElements(actions.toArray());
		Assert.noNullElements(targets.toArray());
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