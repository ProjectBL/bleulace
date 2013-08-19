package com.bleulace.domain.crm.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.web.BusRegisteringViewProvider;
import com.vaadin.navigator.View;

@Component
@Scope("session")
class FrontViewProvider extends BusRegisteringViewProvider
{
	public FrontViewProvider()
	{
		super("frontView");
	}

	@Override
	protected View createView()
	{
		return new FrontView();
	}
}