package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.resource.model.AbstractResource;
import com.vaadin.ui.Window;

@Component
@Scope("prototype")
class ManagerBoxFactoryBean implements FactoryBean<Window>
{
	@Qualifier("managerBoxWindow")
	@Autowired
	private ManagerBox bean;

	private final AbstractResource resource;

	ManagerBoxFactoryBean(AbstractResource resource)
	{
		this.resource = resource;
	}

	@SuppressWarnings("unused")
	private ManagerBoxFactoryBean()
	{
		this(null);
	}

	@Override
	public Window getObject() throws Exception
	{
		bean.setResource(resource);
		return bean;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Window.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}
}