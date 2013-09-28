package com.bleulace.domain.resource.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChildCollectingResourceInspector implements ResourceInspector
{
	private final List<Resource> children = new ArrayList<Resource>();

	public ChildCollectingResourceInspector()
	{
	}

	@Override
	public void inspect(Resource resource)
	{
		children.add(resource);
	}

	public List<Resource> getChildren()
	{
		return children;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getChildren(Class<T> clazz)
	{
		List<T> children = new LinkedList<T>();
		for (Resource child : this.children)
		{
			if (clazz.isAssignableFrom(child.getClass()))
			{
				children.add((T) child);
			}
		}
		return children;
	}
}
