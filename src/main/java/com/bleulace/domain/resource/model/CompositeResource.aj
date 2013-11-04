package com.bleulace.domain.resource.model;

import java.util.List;

public interface CompositeResource extends Resource
{
	void addChild(Resource child);

	void removeChild(Resource child);

	boolean isCompatible(Resource child);

	List<? extends Resource> getChildren();

	public Resource getParent();

	static aspect Impl
	{
		public void CompositeResource.acceptInspector(
				ResourceInspector inspector)
		{
			for (Resource child : this.getChildren())
			{
				child.acceptInspector(inspector);
			}
			inspector.inspect(this);
		}
	}
}