package com.bleulace.utils.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.Navigator.StaticViewProvider;
import com.vaadin.navigator.View;

@Configurable
class SpringViewProvider extends StaticViewProvider
{
	private static final long serialVersionUID = -3847157174338421311L;

	@Autowired
	private ApplicationContext ctx;

	public SpringViewProvider(String viewName)
	{
		super(viewName, null);
	}

	@Override
	public View getView(String viewName)
	{
		return ctx.getBean(viewName, View.class);
	}
}