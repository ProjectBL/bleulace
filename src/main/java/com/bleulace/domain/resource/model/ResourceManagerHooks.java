package com.bleulace.domain.resource.model;

import javax.persistence.PrePersist;

import org.apache.shiro.SecurityUtils;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementRole;
import com.bleulace.domain.management.model.Manager;
import com.bleulace.jpa.EntityManagerReference;

public class ResourceManagerHooks
{
	@PrePersist
	public void prePersist(AbstractResource resource)
	{
		String id = (String) SecurityUtils.getSubject().getPrincipal();
		if (id == null)
		{
			return;
		}
		for (Manager m : resource.getManagers())
		{
			if (m.getAccount().getId().equals(id))
			{
				return;
			}
		}

		resource.getManagers().add(
				new Manager(EntityManagerReference.load(Account.class, id),
						ManagementRole.OWN));
	}
}
