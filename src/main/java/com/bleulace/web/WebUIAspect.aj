package com.bleulace.web;

import org.aspectj.lang.annotation.SuppressAjWarnings;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Component;

privileged aspect WebUIAspect
{
	after(String state) : 
		call(public void Navigator.navigateTo(*)) && args(state)
	{
		SpringApplicationContext.getBean(SecurityContext.class).getSubject()
				.getSession().setAttribute("navState", state);
	}

	@SuppressAjWarnings
	void around(final Component component) : 
		execution(@EnablePush void *(..)) && this(component)
	{
		component.getUI().access(new Runnable()
		{
			@Override
			public void run()
			{
				proceed(component);
			}
		});
	}
}