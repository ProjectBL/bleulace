package com.bleulace.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

public abstract class NavStateConversion extends CustomComponent implements
		View, Converter<ViewChangeEvent, String>
{
	private final String[] entryStates;

	protected NavStateConversion(String... entryStates)
	{
		Assert.noNullElements(entryStates);
		this.entryStates = entryStates;
		setCompositionRoot(new EmptyView());
	}

	public String[] getEntryStates()
	{
		return entryStates;
	}

	@Override
	public final void enter(ViewChangeEvent event)
	{
		event.getNavigator().navigateTo(convert(event));
	}
}