package com.bleulace.mgt.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.shiro.authz.Permission;
import org.springframework.beans.factory.annotation.Configurable;

import com.bleulace.ddd.spec.DisjunctionPermission;

@Configurable
public class ManagementPermission extends SingleManagementPermission implements
		Serializable
{
	private static final long serialVersionUID = 4460107061981274214L;

	@PersistenceContext
	private transient EntityManager em;

	ManagementPermission()
	{
	}

	public ManagementPermission(Project project, ManagementAssignment assignment)
	{
		super(project, assignment);
	}

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof SingleManagementPermission)
		{
			Set<Permission> permissions = new HashSet<Permission>();

			permissions.add(new SingleManagementPermission(getProject(),
					getAssignment()));

			for (String id : getProject().getChildIds())
			{
				permissions.add(new SingleManagementPermission(em.getReference(
						Project.class, id), getAssignment()));
			}

			return new DisjunctionPermission(
					permissions.toArray(new Permission[permissions.size()]))
					.implies(p);
		}
		return false;
	}
}
