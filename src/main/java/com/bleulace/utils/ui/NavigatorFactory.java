package com.bleulace.utils.ui;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

public abstract class NavigatorFactory
{
	public static Navigator make(UI ui)
	{
		Navigator navigator = new Navigator(ui, ui);
		for (String viewName : SpringApplicationContext.get()
				.getBeanNamesForType(View.class))
		{
			navigator.addProvider(new SpringViewProvider(viewName));
		}
		navigator.addViewChangeListener(new EventBusSubscribingListener());
		return navigator;
	}
}