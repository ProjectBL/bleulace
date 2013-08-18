package com.bleulace.utils.ui;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

aspect ServerPushingAspect
{
	void around(final Component component) : 
		execution(@ServerPush void *(..)) && this(component)
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