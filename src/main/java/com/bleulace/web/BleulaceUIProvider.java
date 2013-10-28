package com.bleulace.web;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class BleulaceUIProvider extends UIProvider
{
	public BleulaceUIProvider()
	{
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event)
	{
		return WebUI.class;
	}

	@Override
	public String getTheme(UICreateEvent event)
	{
		try
		{
			return SpringApplicationContext.getBean(BleulaceTheme.class)
					.getThemeName();
		}
		catch (NoUniqueBeanDefinitionException e)
		{
			throw new IllegalStateException("Multiple themes registered.", e);
		}
		catch (NoSuchBeanDefinitionException e)
		{
			throw new IllegalStateException("No theme registered.", e);
		}
	}
}