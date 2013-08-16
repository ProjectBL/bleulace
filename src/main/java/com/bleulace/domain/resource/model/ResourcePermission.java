package com.bleulace.domain.resource.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.utils.authz.GenericPermission;

public class ResourcePermission implements
		GenericPermission<ResourcePermission>
{
	private final String targetId;

	public ResourcePermission(String targetId)
	{
		Assert.notNull(targetId);
		this.targetId = targetId;
	}

	@Override
	public boolean implies(ResourcePermission p)
	{
		return new ResourcePermissionInspector().childIds.contains(p.targetId);
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