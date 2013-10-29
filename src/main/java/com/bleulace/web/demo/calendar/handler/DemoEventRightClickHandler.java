package com.bleulace.web.demo.calendar.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.management.infrastructure.EventDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.annotation.WebProfile;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarDateRange;

@Scope("ui")
@Component
@WebProfile
class DemoEventRightClickHandler implements Handler
{
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
		Subject s = SecurityUtils.getSubject();

		String targetId = (String) s.getSession().getAttribute("targetId");
		String currentId = (String) s.getPrincipal();
		Assert.notNull(currentId);

		if (currentId.equals(targetId))
		{
			Calendar calendar = (Calendar) sender;
			CalendarDateRange range = (CalendarDateRange) target;
			if (calendar.getEvents(range.getStart(), range.getEnd()).size() > 0)
			{
				return actions;
			}
		}
		return null;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		if (target instanceof PersistentEvent)
		{
			RightClickGesture.evaluate(action, (PersistentEvent) target);
		}
	}

	interface RightClickCallback
	{
		void execute(PersistentEvent bean);
	}

	static class RsvpCallback implements RightClickCallback
	{
		private final RsvpStatus status;

		RsvpCallback(RsvpStatus status)
		{
			this.status = status;
		}

		@Override
		public void execute(PersistentEvent event)
		{
			event.setRsvpStatus(SpringApplicationContext.getUser().getId(),
					status);
			SpringApplicationContext.getBean(EventDAO.class).save(event);
			SpringApplicationContext.getBean(Calendar.class).markAsDirty();
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

		static void evaluate(Action action, PersistentEvent event)
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