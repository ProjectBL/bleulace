package com.bleulace.web;

import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@UIComponent
class FrontViewConversion extends NavStateConversion
{
	protected FrontViewConversion()
	{
		super("");
	}

	@Override
	public String convert(ViewChangeEvent source)
	{
		return "frontView";
	}
}