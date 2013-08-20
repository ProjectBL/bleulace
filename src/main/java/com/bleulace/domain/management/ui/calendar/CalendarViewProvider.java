package com.bleulace.domain.management.ui.calendar;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.bleulace.domain.management.ui.calendar.context.CalendarViewContextFactory;
import com.bleulace.web.BusRegisteringViewProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;

@Component
@Scope("session")
class CalendarViewProvider extends BusRegisteringViewProvider
{
	public CalendarViewProvider()
	{
		super("calendarView");
	}

	@Autowired
	private transient CalendarViewContextFactory factor;

	@Override
	protected View createView()
	{
		return new Foo(factor.make(SecurityUtils.getSubject().getId(),
				SecurityUtils.getSubject().getId()));
	}

	class Foo extends CustomComponent implements View
	{
		private final CalendarViewContext ctx;

		public Foo(CalendarViewContext ctx)
		{
			this.ctx = ctx;
		}

		@Override
		public void enter(ViewChangeEvent event)
		{
			setCompositionRoot(new CalendarComponent(ctx));
		}
	}
}
