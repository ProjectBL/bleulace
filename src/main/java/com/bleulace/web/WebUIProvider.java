package com.bleulace.web;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class WebUIProvider extends UIProvider
{
	public WebUIProvider()
	{
	}

	@Override
	public UI createInstance(UICreateEvent event)
	{
		return SpringApplicationContext.getBean(UIInstanceProvider.class)
				.getInstance();
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event)
	{
		return WebUI.class;
	}

	@Override
	public boolean isPreservedOnRefresh(UICreateEvent event)
	{
		return false;
	}
}