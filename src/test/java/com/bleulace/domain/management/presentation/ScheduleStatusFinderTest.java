package com.bleulace.domain.management.presentation;

import java.util.TimeZone;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.Send;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.AccountManager;
import com.bleulace.domain.management.command.CreateEventCommand;
import com.bleulace.domain.management.command.RsvpCommand;
import com.bleulace.domain.management.model.Event;
import com.bleulace.utils.Locator;

public class ScheduleStatusFinderTest implements IntegrationTest,
		CommandGatewayAware
{
	private AccountManager manager;

	@Autowired
	private ScheduleStatusFinder finder;

	private final LocalDateTime start = LocalDateTime.now().plusMinutes(15);
	private final LocalDateTime end = start.plusHours(2);

	@Before
	public void beforeTest()
	{
		manager = new AccountManager().login();
		createEvent();
		rsvp();
	}

	@Test
	public void testFindStatus()
	{
		finder.findScheduleStatus(manager.getId(), start.minusDays(1).toDate(),
				end.plusDays(1).toDate(), TimeZone.getDefault());
	}

	@Send
	public RsvpCommand rsvp()
	{
		return new RsvpCommand(Locator.locate(Event.class).getId(), true);
	}

	@Send
	public CreateEventCommand createEvent()
	{
		return new CreateEventCommand("foo", "bar", start.toDate(),
				end.toDate());
	}
}
