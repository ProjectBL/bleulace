package com.bleulace.domain.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.domain.account.Account;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

@Entity
@RooJavaBean
public class CalendarEntry extends AbstractPersistable<Long> implements
		EditableCalendarEvent
{
	private static final long serialVersionUID = 1831178477077720532L;

	@CascadeOnDelete
	@OneToMany(cascade = { CascadeType.ALL })
	private List<CalendarEntryParticipant> entryParticipants = new ArrayList<CalendarEntryParticipant>();

	@NotEmpty
	@Column(nullable = false)
	private String caption = "";

	@NotEmpty
	@Column(nullable = false)
	private String description = "";

	private String location = "?";

	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;

	@Transient
	private String styleName;

	@Transient
	public boolean allDay = meetsAllDayCriteria();

	public CalendarEntry()
	{
	}

	@Override
	public void setStart(Date start)
	{
		this.start = start;
		updateTimes();
	}

	@Override
	public void setEnd(Date end)
	{
		this.end = end;
		updateTimes();
	}

	@Override
	public boolean isAllDay()
	{
		return false;
	}

	@Override
	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}

	public Map<Account, ParticipationStatus> getParticipants()
	{
		return new ParticipantMap(this);
	}

	public Collection<Account> getAccounts()
	{
		return getParticipants().keySet();
	}

	public void setAccounts(Collection<Account> accounts)
	{
		Set<Account> accountSet = new HashSet<Account>(accounts);
		for (Account account : accountSet)
		{
			ParticipationStatus status = getParticipants().get(account);
			if (status == null)
			{
				getParticipants().put(account, ParticipationStatus.PENDING);
			}
		}
		for (Account account : getAccounts())
		{
			if (!accountSet.contains(account))
			{
				getParticipants().remove(account);
			}
		}
	}

	public ParticipationStatus getStatus()
	{
		Account current = Account.current();
		return current == null ? null : getParticipants().get(current);
	}

	public void setStatus(ParticipationStatus status)
	{
		Account current = Account.current();
		if (current != null)
		{
			getParticipants().put(current, status);
		}
	}

	@PostLoad
	protected void postLoad()
	{
		allDay = meetsAllDayCriteria();
	}

	@PreUpdate
	@PrePersist
	protected void preSave()
	{
		registerOwner();
		updateTimes();
	}

	@PostConstruct
	protected void updateTimes()
	{
		if (start == null)
		{
			start = new LocalTime(LocalTime.now().plusHours(1).getHourOfDay(),
					0).toDateTimeToday().toDate();
		}
		if (end == null)
		{
			end = LocalDateTime.fromDateFields(start).plusHours(1).toDate();
		}
		if (isAllDay())
		{
			makeAllDay();
		}
	}

	private boolean meetsAllDayCriteria()
	{
		if (start == null || end == null)
		{
			return false;
		}
		LocalDateTime startDt = LocalDateTime.fromDateFields(start);
		LocalDateTime endDt = LocalDateTime.fromDateFields(end);

		return startDt.getHourOfDay() == 0 && startDt.getMinuteOfHour() == 0
				&& endDt.getHourOfDay() == 23 && endDt.getMinuteOfHour() == 59
				&& startDt.toLocalDate().equals(endDt.toLocalDate());
	}

	private void makeAllDay()
	{
		LocalDate value = LocalDateTime.fromDateFields(start).toLocalDate();
		start = value.toLocalDateTime(new LocalTime(0, 0)).toDate();
		end = value.toLocalDateTime(new LocalTime(23, 59)).toDate();
	}

	private void registerOwner()
	{
		Account executing = Account.current();
		if (executing != null)
		{
			getParticipants().put(executing, ParticipationStatus.ACCEPTED);
		}
	}
}