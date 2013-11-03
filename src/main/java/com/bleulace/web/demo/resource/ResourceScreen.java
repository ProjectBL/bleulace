package com.bleulace.web.demo.resource;

import org.springframework.util.Assert;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

class ResourceScreen extends CustomComponent
{
	ResourceScreen(EntityItem<?> item)
	{
		Assert.notNull(item);
		setCompositionRoot(new Label("foo"));
		setData(item);
	}

	@Override
	public String toString()
	{
		return getItem().getEntity().toString();
	}

	private EntityItem<?> getItem()
	{
		return (EntityItem<?>) getData();
	}
}