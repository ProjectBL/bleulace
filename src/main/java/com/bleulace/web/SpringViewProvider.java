package com.bleulace.web;

import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.Navigator.StaticViewProvider;
import com.vaadin.navigator.View;

class SpringViewProvider extends StaticViewProvider
{
	private static final long serialVersionUID = -3847157174338421311L;

	private final ApplicationContext ctx;

	public SpringViewProvider(String viewName, ApplicationContext ctx)
	{
		super(viewName, null);
		this.ctx = ctx;
	}

	@Override
	public View getView(String viewName)
	{
		return ctx.getBean(viewName, View.class);
	}
}