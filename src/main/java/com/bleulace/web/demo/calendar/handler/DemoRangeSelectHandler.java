package com.bleulace.web.demo.calendar.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.SystemUser;
import com.bleulace.web.demo.timebox.TimeBox;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@Component
class DemoRangeSelectHandler implements RangeSelectHandler
{
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AccountDAO dao;

	@Autowired
	private SystemUser user;

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		PersistentEvent calendarEvent = new PersistentEvent();

		calendarEvent.setStart(event.getStart());
		calendarEvent.setEnd(event.getEnd());

		List<String> vieweeIds = user.getVieweeIds();
		for (String vieweeId : vieweeIds)
		{
			calendarEvent.setRsvpStatus(vieweeId, RsvpStatus.PENDING);
		}
		String ownerId = SpringApplicationContext.getUser().getId();
		if (vieweeIds.contains(ownerId))
		{
			calendarEvent.setRsvpStatus(ownerId, RsvpStatus.ACCEPTED);
		}

		ctx.getBean(TimeBox.class).show(calendarEvent);
	}
}