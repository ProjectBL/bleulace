package com.bleulace.domain.management.ui.calendar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.management.ui.calendar.context.CalendarViewContext;
import com.vaadin.ui.components.calendar.event.BasicEvent;

@RooJavaBean
class EventModelImpl extends BasicEvent implements EventModel
{
	private final String id;

	private final CalendarViewContext context;

	@Override
	@NotEmpty
	public String getCaption()
	{
		return super.getCaption();
	}

	@Override
	@NotNull
	public String getDescription()
	{
		return super.getDescription();
	}

	@Override
	@Future
	public Date getStart()
	{
		return super.getStart();
	}

	@Override
	@Future
	public Date getEnd()
	{
		return super.getEnd();
	}

	@NotNull
	private List<String> inviteeIds = new ArrayList<String>();

	EventModelImpl(String id, CalendarViewContext context)
	{
		this.id = id;
		this.context = context;
		setCaption("");
		setDescription("");
	}

	@Override
	public boolean isNew()
	{
		return id == null;
	}
}