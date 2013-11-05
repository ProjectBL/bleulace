package com.bleulace.web.demo.resource;

import com.vaadin.addon.jpacontainer.EntityItem;

public class ResourceSelectedEvent
{
	private final EntityItem<?> item;

	public ResourceSelectedEvent(EntityItem<?> item)
	{
		this.item = item;
	}

	public EntityItem<?> getItem()
	{
		return item;
	}
}
