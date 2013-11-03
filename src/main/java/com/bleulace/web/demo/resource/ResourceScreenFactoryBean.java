package com.bleulace.web.demo.resource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;

@Lazy
@Scope("prototype")
@org.springframework.stereotype.Component("resourceScreen")
class ResourceScreenFactoryBean implements FactoryBean<Component>
{
	private EntityItem<?> item;

	ResourceScreenFactoryBean(EntityItem<?> item)
	{
		this.item = item;
	}

	private ResourceScreenFactoryBean()
	{
	}

	@Override
	public Component getObject() throws Exception
	{
		return item == null ? new AbsoluteLayout() : new ResourceScreen(item);
	}

	@Override
	public Class<?> getObjectType()
	{
		return Component.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

}
