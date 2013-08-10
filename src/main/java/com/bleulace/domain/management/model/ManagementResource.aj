package com.bleulace.domain.management.model;

import java.util.List;

import javax.persistence.Column;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.resource.model.CompositeResource;

public interface ManagementResource extends CompositeResource
{
	static aspect Impl
	{
		@Column(nullable = false)
		private String ManagementResource.title = "";

		public String ManagementResource.getTitle()
		{
			return this.title;
		}

		public void ManagementResource.setTitle(String title)
		{
			this.title = title;
		}

		public List<Account> ManagementResource.getManagers(ManagementRole role)
		{
			return ManagementRoleAssignment.findManagers(this.getId(), role);
		}

		public void ManagementResource.on(ManagerAssignedEvent event)
		{
			if (event.getRole() == null)
			{
				ManagementRoleAssignment.revoke(this.getId(),
						event.getAssigneeId());
			}
			else
			{
				ManagementRoleAssignment.assign(this.getId(),
						event.getAssigneeId(), event.getRole());
			}
		}
	}
}