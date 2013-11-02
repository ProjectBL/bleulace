package com.bleulace.web;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.StaticViewProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

@Scope("prototype")
@Component("navigator")
class NavigatorFactoryBean implements FactoryBean<Navigator>
{
	@Autowired
	private ApplicationContext ctx;

	private Navigator navigator;

	NavigatorFactoryBean(UI ui)
	{
		navigator = new Navigator(ui, ui);
	}

	@SuppressWarnings("unused")
	private NavigatorFactoryBean()
	{
	}

	@Override
	public Navigator getObject() throws Exception
	{
		for (String viewName : ctx.getBeanNamesForType(View.class, true, false))
		{
			navigator.addProvider(new LazyViewProvider(viewName));
		}
		return navigator;
	}

	@Override
	public Class<?> getObjectType()
	{
		return Navigator.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	private class LazyViewProvider extends StaticViewProvider
	{
		LazyViewProvider(String viewName)
		{
			super(viewName, null);
		}

		@Override
		public View getView(String viewName)
		{
			if (this.getViewName().equals(viewName))
			{
				return ctx.getBean(viewName, View.class);
			}
			return null;
		}
	}
}
