package com.bleulace.web.demo;

import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.web.demo.timebox.EventBean;
import com.bleulace.web.demo.timebox.TimeBox;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;

@UIComponent("calendar")
class DemoCalendarView extends CustomComponent implements View,
		RangeSelectHandler, EventClickHandler, Handler
{
	private final Calendar calendar = makeCalendar();

	// WARNING: quick and dirty
	private final BasicEventProvider provider = (BasicEventProvider) calendar
			.getEventProvider();

	private final Action[] actions = makeActions();

	public DemoCalendarView()
	{
		setSizeFull();
		setCompositionRoot(calendar);
		calendar.setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
	}

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		EventBean bean = new EventBean();
		bean.setStart(event.getStart());
		bean.setEnd(event.getEnd());
		// calendar.addEvent(bean);
		getUI().addWindow(new TimeBox(bean, provider));
	}

	@Override
	public void eventClick(EventClick event)
	{
		EventBean bean = (EventBean) event.getCalendarEvent();
		Window w = new TimeBox(bean, provider);
		getUI().addWindow(w);
		w.focus();
	}

	@Override
	public Action[] getActions(Object target, Object sender)
	{
		CalendarDateRange range = (CalendarDateRange) target;
		if (calendar.getEvents(range.getStart(), range.getEnd()).size() > 0)
		{
			return actions;
		}
		return null;
	}

	private Calendar makeCalendar()
	{
		Calendar calendar = new Calendar();
		calendar.setHandler((RangeSelectHandler) this);
		calendar.setHandler((EventClickHandler) this);
		calendar.setCaption("Event Calendar");
		calendar.addActionHandler(this);
		return calendar;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		if (target instanceof EventBean)
		{
			EventBean bean = (EventBean) target;
			for (RightClickGesture gesture : RightClickGesture.values())
			{
				if (gesture.matches(action))
				{
					gesture.command.execute(bean);
				}
			}
		}
	}

	private Action[] makeActions()
	{
		int length = RightClickGesture.values().length;
		Action[] actions = new Action[length];
		for (int i = 0; i < length; i++)
		{
			actions[i] = RightClickGesture.values()[i].action;
		}
		return actions;
	}

	private enum RightClickGesture
	{
		ACCEPT("Accept event", new RsvpCommand(RsvpStatus.ACCEPTED)), DECLINE(
				"Decline event", new RsvpCommand(RsvpStatus.DECLINED));

		final Action action;
		final EventBeanCommand command;

		RightClickGesture(String caption, EventBeanCommand command)
		{
			this.action = new Action(caption);
			this.command = command;
		}

		boolean matches(Action action)
		{
			return action.getCaption().equals(this.action.getCaption());
		}
	}

	private interface EventBeanCommand
	{
		void execute(EventBean bean);
	}

	private static class RsvpCommand implements EventBeanCommand
	{
		final RsvpStatus status;

		RsvpCommand(RsvpStatus status)
		{
			this.status = status;
		}

		@Override
		public void execute(EventBean bean)
		{
			bean.setStatus(status);
		}
	}
}