package com.bleulace.domain.management.model;

import javax.persistence.Column;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.CommentableResource;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.EntityManagerReference;

public interface ManageableResource extends CommentableResource
{
	static aspect Impl
	{
		public boolean ManageableResource.isComplete()
		{
			return getProgress().isComplete();
		}

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

		public Progress ManageableResource.getProgress()
		{
			ProgressCalculatingInspector visitor = new ProgressCalculatingInspector();
			this.acceptInspector(visitor);
			return visitor.getProgress();
		}

		public void ManageableResource.on(ManagerAssignedEvent event)
		{
			this.addChild(new ManagementAssignment((AbstractResource) this,
					EntityManagerReference.load(Account.class,
							event.getAssigneeId()), event.getRole()));
		}
	}
}