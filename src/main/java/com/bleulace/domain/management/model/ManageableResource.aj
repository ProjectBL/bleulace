package com.bleulace.domain.management.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.domain.resource.model.CompositeResource;

public interface ManageableResource extends CompositeResource
{
	public boolean isComplete();

	public void setComplete(boolean complete);

	static aspect Impl
	{
		@NotEmpty
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

		private boolean ManageableResource.isManager(String accountId)
		{
			return this.getAssignments().get(accountId) != null;
		}
	}
}