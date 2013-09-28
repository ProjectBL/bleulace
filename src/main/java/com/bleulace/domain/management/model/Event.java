package com.bleulace.domain.management.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.PreRemove;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.DateWindow;
import com.bleulace.jpa.EntityManagerReference;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Entity
@RooJavaBean
public class Event extends Project implements CalendarEvent
{
	@Embedded
	private DateWindow window;

	@MapKeyColumn(name = "GUEST_ID")
	@ElementCollection
	private Map<Account, EventInvitee> invitees = new HashMap<Account, EventInvitee>();

	@Column(nullable = false)
	private String location = "";

	Event()
	{
	}

	public RsvpStatus getRsvpStatus(String accountId)
	{
		EventInvitee invitee = invitees.get(EntityManagerReference.load(
				Account.class, accountId));
		return invitee == null ? null : invitee.getStatus();
	}

	@PreRemove
	protected void preRemove()
	{
		for (Account a : invitees.keySet())
		{
			invitees.remove(a);
		}
	}

	@Override
	public Date getStart()
	{
		return window.getStart();
	}

	@Override
	public Date getEnd()
	{
		return window.getEnd();
	}

	@Override
	public String getCaption()
	{
		return getTitle();
	}

	@Override
	public String getDescription()
	{
		return getLocation();
	}

	@Override
	public String getStyleName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAllDay()
	{
		// TODO Auto-generated method stub
		return false;
	}
}