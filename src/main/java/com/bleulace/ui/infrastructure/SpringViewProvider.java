package com.bleulace.ui.infrastructure;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.navigator.Navigator.StaticViewProvider;
import com.vaadin.navigator.View;

class SpringViewProvider extends StaticViewProvider
{
	private static final long serialVersionUID = -3847157174338421311L;

	public SpringViewProvider(String viewName)
	{
		super(viewName, null);
	}

	@Override
	public View getView(String viewName)
	{
		try
		{
			return SpringApplicationContext.get().getBean(viewName, View.class);
		}
		catch (NoSuchBeanDefinitionException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}