package com.bleulace.web.demo.calendar;

import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.web.annotation.WebProfile;
import com.bleulace.web.demo.timebox.EventBean;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarDateRange;

@Component
@WebProfile
class CalendarRightClickHandler implements Handler
{
	private final Action[] actions;

	CalendarRightClickHandler()
	{
		int length = RightClickGesture.values().length;
		actions = new Action[length];
		for (int i = 0; i < length; i++)
		{
			actions[i] = RightClickGesture.values()[i].action;
		}
	}

	@Override
	public Action[] getActions(Object target, Object sender)
	{
		Calendar calendar = (Calendar) sender;
		CalendarDateRange range = (CalendarDateRange) target;
		if (calendar.getEvents(range.getStart(), range.getEnd()).size() > 0)
		{
			return actions;
		}
		return null;
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

	interface EventBeanCommand
	{
		void execute(EventBean bean);
	}

	enum RightClickGesture
	{
		//@formatter:off
		ACCEPT("accept", new RsvpCommand(RsvpStatus.ACCEPTED)), 
		DECLINE("decline", new RsvpCommand(RsvpStatus.DECLINED));
		//@formatter:on

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