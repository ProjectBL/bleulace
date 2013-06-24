package com.bleulace.ui.web.calendar;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

public class CalendarView extends CustomComponent implements View
{
	private static final long serialVersionUID = 204160537464921358L;

	public static final String NAME = "calendar";

	@Override
	public void enter(ViewChangeEvent event)
	{
		setCompositionRoot(new CalendarComponent());
	}
}