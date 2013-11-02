package com.bleulace.domain.management.model;

import com.bleulace.domain.resource.model.CompositeResource;

public interface ManageableResource extends CompositeResource
{
	public boolean isComplete();

	public void setComplete(boolean complete);

	public String getTitle();

	public void setTitle(String title);

	static aspect Impl
	{
		public Progress ManageableResource.getProgress()
		{
			ProgressCalculatingInspector visitor = new ProgressCalculatingInspector();
			this.acceptInspector(visitor);
			return visitor.getProgress();
		}
	}
}