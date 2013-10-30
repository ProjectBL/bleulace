package com.bleulace.domain.management.model;

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

		public Progress ManageableResource.getProgress()
		{
			ProgressCalculatingInspector visitor = new ProgressCalculatingInspector();
			this.acceptInspector(visitor);
			return visitor.getProgress();
		}
	}
}