package com.bleulace.domain.resource.model;

import java.util.ArrayList;
import java.util.List;

public interface CompositeResource extends Resource
{
	void addChild(Resource child);

	void removeChild(Resource child);

	boolean isCompatible(Resource child);

	List<? extends Resource> getChildren();
	
	static aspect Impl
	{
		@SuppressWarnings("unchecked")
		public <T extends Resource> List<T> CompositeResource.getChildren(
				Class<T> clazz)
		{
			List<T> childList = new ArrayList<T>();
			for (Resource child : this.getChildren())
			{
				if (child.getClass().isAssignableFrom(clazz))
				{
					childList.add((T) child);
				}
			}
			return childList;
		}
	}
}