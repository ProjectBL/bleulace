package com.bleulace.web.demo.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.web.annotation.WebProfile;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarDateRange;

@Scope("ui")
@Component
@WebProfile
class CalendarRightClickHandler implements Handler
{
	@Autowired
	private CalendarPresenter presenter;

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
		if (target instanceof PersistentEvent)
		{
			PersistentEvent bean = (PersistentEvent) target;
			for (RightClickGesture gesture : RightClickGesture.values())
			{
				if (gesture.matches(action))
				{
					switch (gesture)
					{
					case ACCEPT:
						presenter.eventAccepted(bean);
						break;
					case DECLINE:
						presenter.eventDeclined(bean);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	enum RightClickGesture
	{
		ACCEPT("accept"), DECLINE("decline");

		final Action action;

		RightClickGesture(String caption)
		{
			this.action = new Action(caption);
		}

		boolean matches(Action action)
		{
			return action.getCaption().equals(this.action.getCaption());
		}
	}
}