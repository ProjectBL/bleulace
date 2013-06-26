package com.bleulace.domain.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Persistable;

import com.bleulace.domain.QueryFactory;
import com.bleulace.domain.account.Account;
import com.vaadin.ui.components.calendar.event.BasicEvent;

@Entity
public class JPACalendarEvent extends BasicEvent implements Persistable<String>
{
	private static final long serialVersionUID = -1433376710685791516L;

	private String id = UUID.randomUUID().toString();

	private List<CalendarEntryParticipant> eventParticipants = new ArrayList<CalendarEntryParticipant>();

	private boolean transientFlag = true;

	public JPACalendarEvent()
	{
		super();
		initializeDefaults();
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getId()
	{
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(String id)
	{
		this.id = id;
	}

	@NotEmpty
	@Column
	@Override
	public String getCaption()
	{
		return super.getCaption();
	}

	@Override
	@Column
	public String getDescription()
	{
		return super.getDescription();
	}

	@Future
	@NotNull
	@Override
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStart()
	{
		return super.getStart();
	}

	@Future
	@NotNull
	@Override
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEnd()
	{
		return super.getEnd();
	}

	@CascadeOnDelete
	@OneToMany(cascade = { CascadeType.ALL })
	public List<CalendarEntryParticipant> getEntryParticipants()
	{
		return eventParticipants;
	}

	public void setEntryParticipants(
			List<CalendarEntryParticipant> entryParticipants)
	{
		this.eventParticipants = entryParticipants;
		fireEventChange();
	}

	@Transient
	public Map<Account, ParticipationStatus> getParticipants()
	{
		return new ParticipantMap(this);
	}

	@Transient
	public Collection<Account> getAccounts()
	{
		return getParticipants().keySet();
	}

	@Transient
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
		fireEventChange();
	}

	@Override
	public boolean isNew()
	{
		return isTransientFlag();
	}

	@Override
	public boolean equals(Object obj)
	{

		if (null == obj)
		{
			return false;
		}

		if (this == obj)
		{
			return true;
		}

		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		JPACalendarEvent that = (JPACalendarEvent) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	protected void initializeDefaults()
	{
		setCaption("");
		setDescription("");

		LocalDateTime now = LocalDateTime.now();

		setStart(now.plusHours(1).toDate());
		setEnd(now.plusHours(2).toDate());
		setAllDay(false);
	}

	@PrePersist
	protected void prePersist()
	{
		registerOwner();
	}

	@PostPersist
	@PostLoad
	protected void postLoadOrPersist()
	{
		setTransientFlag(false);
	}

	private void registerOwner()
	{
		Account executing = Account.current();
		if (executing != null)
		{
			getParticipants().put(executing, ParticipationStatus.HOST);
		}
	}

	@Transient
	private boolean isTransientFlag()
	{
		return transientFlag;
	}

	private void setTransientFlag(boolean transientFlag)
	{
		this.transientFlag = transientFlag;
	}

	public static List<JPACalendarEvent> findByAccounts(Account... accounts)
	{
		QCalendarEntryParticipant p = QCalendarEntryParticipant.calendarEntryParticipant;
		return QueryFactory.from(p).where(p.account.in(accounts)).list(p.entry);
	}
}