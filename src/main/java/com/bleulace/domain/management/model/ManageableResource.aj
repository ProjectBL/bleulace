package com.bleulace.domain.management.model;

import java.util.List;

import javax.persistence.Column;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.CommentableResource;
import com.bleulace.domain.management.event.ManagerAssignedEvent;

public interface ManageableResource extends CommentableResource
{
	public void accept(ManageableResourceVisitor visitor);

	static aspect Impl
	{
		@Column(nullable = false)
		private String ManageableResource.title = "";

		public String ManageableResource.getTitle()
		{
			return this.title;
		}

		public void ManageableResource.setTitle(String title)
		{
			this.title = title;
		}

		public List<Account> ManageableResource.getManagers(ManagementRole role)
		{
			return ManagementRoleAssignment.findManagers(this.getId(), role);
		}

		public Progress ManageableResource.getProgress()
		{
			ProgressCalculatingVisitor visitor = new ProgressCalculatingVisitor();
			this.accept(visitor);
			return visitor.getProgress();
		}

		public void ManageableResource.on(ManagerAssignedEvent event)
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

		public void ManageableResource.accept(ManageableResourceVisitor visitor)
		{
			if (!this.isLeaf())
			{
				for (ManageableResource child : this
						.getChildren(ManageableResource.class))
				{
					child.accept(visitor);
				}
			}
			visitor.visit(this);
		}
	}
}