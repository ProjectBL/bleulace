package com.bleulace.domain.management.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MapKeyColumn;

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

		@MapKeyColumn(name = "MANAGER_ID")
		@ElementCollection
		private Map<Account, ManagementAssignment> assignments = new HashMap<Account, ManagementAssignment>();

		public String ManageableResource.getTitle()
		{
			return this.title;
		}

		public void ManageableResource.setTitle(String title)
		{
			this.title = title;
		}

		public Progress ManageableResource.getProgress()
		{
			ProgressCalculatingVisitor visitor = new ProgressCalculatingVisitor();
			this.accept(visitor);
			return visitor.getProgress();
		}

		public void ManageableResource.on(ManagerAssignedEvent event)
		{
			//TODO : fix me
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