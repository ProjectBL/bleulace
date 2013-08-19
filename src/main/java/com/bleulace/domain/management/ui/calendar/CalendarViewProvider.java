package com.bleulace.domain.management.ui.calendar;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.web.BusRegisteringViewProvider;
import com.vaadin.navigator.View;

@Component
@Scope("session")
class CalendarViewProvider extends BusRegisteringViewProvider
{
	public CalendarViewProvider()
	{
		super("calendarView");
	}

	@Override
	protected View createView()
	{
		CalendarViewImpl impl = new CalendarViewImpl();
		impl.addViewListener(new CalendarPresenter());
		return impl;
	}

}
