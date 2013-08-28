package com.bleulace.web;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.annotation.SuppressAjWarnings;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Component;

privileged aspect WebUIAspect
{
	after(String state) : 
		call(public void Navigator.navigateTo(*)) && args(state)
	{
		SecurityUtils.getSubject().getSession().setAttribute("navState", state);
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