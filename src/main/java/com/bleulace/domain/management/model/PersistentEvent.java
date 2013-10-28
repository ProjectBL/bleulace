package com.bleulace.domain.management.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.jpa.EntityManagerReference;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

@RooJavaBean
@Entity
public class PersistentEvent extends Project implements EditableCalendarEvent
{
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date start;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date end;

	@MapKeyColumn(name = "GUEST_ID")
	@ElementCollection
	private Map<Account, EventInvitee> invitees = new HashMap<Account, EventInvitee>();

	@NotEmpty
	@Column(nullable = false)
	private String location = "";

	public PersistentEvent()
	{
	}

	public RsvpStatus getRsvpStatus(String accountId)
	{
		EventInvitee invitee = invitees.get(EntityManagerReference.load(
				Account.class, accountId));
		return invitee == null ? null : invitee.getStatus();
	}

	public void setRsvpStatus(String accountId, RsvpStatus status)
	{
		Account guest = EntityManagerReference.load(Account.class, accountId);
		EventInvitee invitee = invitees.get(guest);
		if (invitee == null)
		{
			invitee = new EventInvitee(guest, Account.getCurrent());
			invitees.put(guest, invitee);
		}
		invitee.setStatus(status);
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
	public String getCaption()
	{
		return getTitle();
	}

	@Override
	public void setCaption(String caption)
	{
		setTitle(caption);
	}

	@Override
	public String getDescription()
	{
		return location;
	}

	@Override
	public void setDescription(String description)
	{
		setLocation(description);
	}

	@Override
	public String getStyleName()
	{
		Account current = Account.getCurrent();
		if (current != null)
		{
			EventInvitee invitee = getInvitees().get(current);
			return null == invitee ? null : invitee.getStatus().getStyleName();
		}
		return null;
	}

	@Override
	public String toString()
	{
		return getCaption();
	}

	@Override
	public void setStyleName(String styleName)
	{
	}

	@Override
	public boolean isAllDay()
	{
		return false;
	}

	@Override
	public void setAllDay(boolean isAllDay)
	{
	}
}