package com.bleulace.web.demo.timebox;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.bleulace.domain.management.model.RsvpStatus;
import com.vaadin.ui.components.calendar.event.BasicEvent;

public class EventBean extends BasicEvent
{
	@NotEmpty
	private Set<PersonBean> attendees = new HashSet<PersonBean>();

	private RsvpStatus status = RsvpStatus.PENDING;

	public EventBean()
	{
		setCaption("");
		setDescription("");
	}

	@Override
	@NotEmpty
	public String getCaption()
	{
		return super.getCaption();
	}

	@Override
	@NotEmpty
	public String getDescription()
	{
		return super.getDescription();
	}

	@Override
	@NotNull
	@Future
	public Date getStart()
	{
		return super.getStart();
	}

	@Override
	@NotNull
	@Future
	public Date getEnd()
	{
		return super.getEnd();
	}

	public Set<PersonBean> getAttendees()
	{
		return attendees;
	}

	public void setAttendees(Set<PersonBean> attendees)
	{
		this.attendees = attendees;
		fireEventChange();
	}

	public RsvpStatus getStatus()
	{
		return status;
	}

	public void setStatus(RsvpStatus status)
	{
		this.status = status;
		fireEventChange();
	}

	@Override
	public String getStyleName()
	{
		return status == null ? null : status.getStyleName();
	}
}