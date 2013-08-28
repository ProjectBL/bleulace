package com.bleulace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.UI;

@Scope("request")
@Component
class UIInstanceProvider
{
	@Autowired
	private ApplicationContext ctx;

	private UI current;

	UI getInstance()
	{
		if (current != null)
		{
			for (String viewName : ctx.getBeansWithAnnotation(VaadinView.class)
					.keySet())
			{
				current.getNavigator().removeView(viewName);
			}
		}
		current = ctx.getBean(WebUI.class);
		return current;
	}
}