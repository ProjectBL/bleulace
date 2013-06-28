package com.bleulace.domain.calendar.command;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.domain.Command;
import com.bleulace.domain.account.Account;
import com.bleulace.domain.calendar.JPACalendarEvent;
import com.bleulace.domain.calendar.ParticipationStatus;
import com.frugalu.api.messaging.jpa.EntityManagerReference;

@RooJavaBean
public class CreateEventCommand implements Command
{
	public CreateEventCommand()
	{
	}

	@NotEmpty
	private String caption = "";

	private String description = "";

	@Future
	@NotNull
	private Date start;

	@Future
	@NotNull
	private Date end;

	@Min(1)
	@SuppressWarnings("unused")
	private int difference()
	{
		return start.compareTo(end);
	}

	private Set<Account> participants = new HashSet<Account>();

	@Override
	@Transactional
	public void execute()
	{
		JPACalendarEvent event = new ModelMapper().map(this,
				JPACalendarEvent.class);
		Account current = Account.current();
		if (current != null)
		{
			event.getParticipants().put(current, ParticipationStatus.ACCEPTED);
		}
		EntityManagerReference.get().persist(event);
	}
}
