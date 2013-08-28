package com.bleulace.web;

import org.aspectj.lang.annotation.SuppressAjWarnings;

import com.vaadin.ui.Component;

privileged aspect PushEnablingAspect
{
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