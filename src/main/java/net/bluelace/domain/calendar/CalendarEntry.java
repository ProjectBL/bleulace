package net.bluelace.domain.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import net.bluelace.domain.account.Account;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
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

	public void setStart(Date start)
	{
		this.start = start;
		updateTimes();
	}

	public void setEnd(Date end)
	{
		this.end = end;
		updateTimes();
	}

	@Override
	public boolean isAllDay()
	{
		return allDay;
	}

	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
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

	@PostLoad
	protected void postLoad()
	{
		allDay = meetsAllDayCriteria();
	}

	@PrePersist
	protected void prePersist()
	{
		registerOwner();
		updateTimes();
	}

	@PreUpdate
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
			participants.put(executing, ParticipationStatus.ACCEPTED);
			// add permissions
		}
	}
}