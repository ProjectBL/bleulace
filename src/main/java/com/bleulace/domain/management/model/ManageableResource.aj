package com.bleulace.domain.management.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.CommentableResource;
import com.bleulace.domain.management.event.ManagerAssignedEvent;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.jpa.config.QueryFactory;

public interface ManageableResource extends CommentableResource
{
	static aspect Impl
	{
		private static final transient QManagementAssignment Q_ASSIGNMENT = QManagementAssignment.managementAssignment;

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

		// TODO : optimize
		public Map<String, ManagementAssignment> ManageableResource.getAssignments()
		{
			Map<String, ManagementAssignment> assignments = new HashMap<String, ManagementAssignment>();
			for (ManagementAssignment assignment : this
					.getChildren(ManagementAssignment.class))
			{
				assignments.put(assignment.getAccount().getId(), assignment);
			}
			return assignments;
		}

		public Progress ManageableResource.getProgress()
		{
			ProgressCalculatingInspector visitor = new ProgressCalculatingInspector();
			this.acceptInspector(visitor);
			return visitor.getProgress();
		}

		public void ManageableResource.on(ManagerAssignedEvent event)
		{
			if (!isManager(event.getAssigneeId()))
			{
				this.addChild(new ManagementAssignment((AbstractResource) this,
						EntityManagerReference.load(Account.class,
								event.getAssigneeId()), event.getRole()));
			}
		}

		private boolean ManageableResource.isManager(String accountId)
		{
			return QueryFactory
					.from(Q_ASSIGNMENT)
					.where(Q_ASSIGNMENT.resource.id.eq(this.getId()).and(
							Q_ASSIGNMENT.account.id.eq(accountId))).exists();
		}
	}
}