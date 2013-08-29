package com.bleulace.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@Component
@Scope("prototype")
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