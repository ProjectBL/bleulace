package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.demo.timebox.TimeBox;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

@Component
class DemoRangeSelectHandler implements RangeSelectHandler
{
	@Autowired
	private AccountDAO dao;

	@Autowired
	private ApplicationContext ctx;

	@Override
	public void rangeSelect(RangeSelectEvent event)
	{
		Account current = findAccount(SpringApplicationContext.getUser()
				.getId());

		Account owner = findAccount(SpringApplicationContext.getUser()
				.getTargetId());

		PersistentEvent calendarEvent = new PersistentEvent();
		calendarEvent.setStart(event.getStart());
		calendarEvent.setEnd(event.getEnd());

		calendarEvent.getInvitees()
				.put(owner, new EventInvitee(owner, current));

		calendarEvent.getInvitees().put(current,
				new EventInvitee(current, current, RsvpStatus.ACCEPTED));

		ctx.getBean(TimeBox.class).show(calendarEvent);
	}

	private Account findAccount(String id)
	{
		Account account = dao.findOne(id);
		Assert.notNull(account);
		return account;
	}
}