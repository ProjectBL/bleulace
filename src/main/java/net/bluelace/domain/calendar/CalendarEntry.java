package net.bluelace.domain.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.bluelace.domain.account.Account;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.vaadin.ui.components.calendar.event.CalendarEvent;

@Entity
@RooJavaBean
public class CalendarEntry extends AbstractPersistable<Long> implements
		CalendarEvent
{
	private static final long serialVersionUID = 1831178477077720532L;

	@Enumerated(EnumType.STRING)
	@MapKeyJoinColumn(name = "ACCOUNT_ID", unique = false, updatable = false)
	private Map<Account, ParticipationStatus> participants = new HashMap<Account, ParticipationStatus>();

	@Column(nullable = false)
	private String caption = "";

	@Column(nullable = false)
	private String description = "";

	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	@Transient
	private String styleName;

	public CalendarEntry()
	{
	}

	@Override
	public boolean isAllDay()
	{
		return start == null || end == null;
	}

	public void addParticipants(Account... participants)
	{
		for (Account participant : participants)
		{
			if (!this.participants.containsKey(participant))
			{
				this.participants.put(participant, ParticipationStatus.PENDING);
			}
		}
	}
}