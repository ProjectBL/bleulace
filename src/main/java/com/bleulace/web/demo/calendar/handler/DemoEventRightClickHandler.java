package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.WebProfile;
import com.bleulace.web.demo.calendar.CalendarEventAdapter;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarDateRange;

@Scope("prototype")
@Component
@WebProfile
class DemoEventRightClickHandler implements Handler
{
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private SystemUser user;

	private final Action[] actions;

	DemoEventRightClickHandler()
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
		CalendarDateRange range = (CalendarDateRange) target;
		Calendar cal = (Calendar) sender;
		CachingEventProvider ep = (CachingEventProvider) cal.getEventProvider();
		return ep.containsRange(range) ? actions : null;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		if (target instanceof CalendarEventAdapter)
		{
			RightClickGesture.evaluate(action, (CalendarEventAdapter) target);
			// eventDAO.save((PersistentEvent) target);
			// calendar.markAsDirty();
		}
	}

	interface RightClickCallback
	{
		void execute(CalendarEventAdapter event);
	}

	static class RsvpCallback implements RightClickCallback
	{
		private final RsvpStatus status;

		RsvpCallback(RsvpStatus status)
		{
			this.status = status;
		}

		@Override
		public void execute(CalendarEventAdapter event)
		{
			for (EventInvitee i : event.getInvitees())
			{
				if (i.getAccount().getId()
						.equals(SpringApplicationContext.getUser().getId()))
				{
					i.setValue(status);
					return;
				}
			}
		}
	}

	enum RightClickGesture
	{
		//@formatter:off
		ACCEPT("Accept", RsvpStatus.ACCEPTED), 
		DECLINE("Decline",RsvpStatus.DECLINED);
		//@formatter:on

		final Action action;
		final RightClickCallback callback;

		RightClickGesture(String caption, RsvpStatus status)
		{
			this.action = new Action(caption);
			this.callback = new RsvpCallback(status);
		}

		static void evaluate(Action action, CalendarEventAdapter event)
		{
			for (RightClickGesture gesture : RightClickGesture.values())
			{
				if (gesture.matches(action))
				{
					gesture.callback.execute(event);
				}
			}
		}

		private boolean matches(Action action)
		{
			return action.getCaption().equals(this.action.getCaption());
		}
	}
}