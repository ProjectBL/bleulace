package com.bleulace.domain.resource.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.springframework.util.Assert;

import com.bleulace.jpa.EntityManagerReference;

public class ResourcePermission implements Permission
{
	private final String targetId;

	public ResourcePermission(String targetId)
	{
		Assert.notNull(targetId);
		this.targetId = targetId;
	}

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof ResourcePermission)
		{
			return new ResourcePermissionInspector().childIds
					.contains(((ResourcePermission) p).targetId);
		}
		return false;
	}

	class ResourcePermissionInspector implements ResourceInspector
	{
		private final Set<String> childIds = new HashSet<String>();

		public ResourcePermissionInspector()
		{
			EntityManagerReference.load(AbstractResource.class, targetId)
					.acceptInspector(this);
		}

		@Override
		public void inspect(Resource resource)
		{
			childIds.add(resource.getId());
		}
	}
}