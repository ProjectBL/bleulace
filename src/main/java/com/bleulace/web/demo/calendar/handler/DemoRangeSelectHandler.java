package com.bleulace.web.demo.calendar.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.RequiresAuthorization;
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
	@RequiresAuthorization(types = "calendar", actions = "create")
	public void rangeSelect(RangeSelectEvent event)
	{
	}
}
