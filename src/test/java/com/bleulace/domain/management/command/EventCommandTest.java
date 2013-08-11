package com.bleulace.domain.management.command;

import java.util.Date;

import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.CreateAccountCommand;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.Event;
import com.bleulace.utils.Locator;
import com.bleulace.utils.jpa.DateWindow;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class EventCommandTest implements CommandGatewayAware
{
	@Autowired
	@Qualifier("createEventCommand")
	private CreateEventCommand createEventCommand;

	@Autowired
	private CreateAccountCommand createAccountCommand;

	@Before
	public void beforeMethod()
	{
		sendAndWait(createEventCommand);
	}

	@Test
	public void testCreateEventCommand()
	{
		Assert.assertTrue(Locator.exists(Event.class));
	}

	@Test
	public void testMoveEvent()
	{

		DateWindow oldDates = Locator.locate(Event.class).getWindow();

		final int minutes = 15;
		sendAndWait(new RescheduleEventCommand(eventId(), LocalDateTime
				.fromDateFields(oldDates.getStart()).plusMinutes(minutes)
				.toDate()));

		DateWindow newDates = Locator.locate(Event.class).getWindow();

		Assert.assertEquals(oldDates.getLength(), newDates.getLength());
		Assert.assertEquals(
				Minutes.minutes(minutes),
				Minutes.minutesBetween(oldDates.getStartTime(),
						newDates.getStartTime()));

	}

	@Test
	public void testResizeEvent()
	{
		Date newStart = LocalDateTime.now().plusMinutes(15).toDate();
		Date newEnd = LocalDateTime.now().plusMinutes(60).toDate();

		sendAndWait(new RescheduleEventCommand(eventId(), newStart, newEnd));
		DateWindow newDates = Locator.locate(Event.class).getWindow();

		Assert.assertTrue(closeEnough(newStart, newDates.getStart()));
		Assert.assertTrue(closeEnough(newEnd, newDates.getEnd()));
	}

	@Test
	public void testRsvpCommand()
	{
		sendAndWait(createAccountCommand);
		doRsvpAndAssertion(true);
		doRsvpAndAssertion(false);
	}

	private void doRsvpAndAssertion(boolean rsvp)
	{
		sendAndWait(new RsvpCommand(eventId(), accountId(), rsvp));
		assertRsvp(rsvp);
	}

	private void assertRsvp(boolean rsvp)
	{
		Assert.assertTrue(rsvp == Locator.locate(Event.class).getAttendees()
				.contains(account()));
	}

	private String eventId()
	{
		return Locator.locate(Event.class).getId();
	}

	private Account account()
	{
		return Locator.locate(Account.class);
	}

	private String accountId()
	{
		return account().getId();
	}

	private boolean closeEnough(Date o1, Date o2)
	{
		return Period.fieldDifference(LocalDateTime.fromDateFields(o1),
				LocalDateTime.fromDateFields(o2)).getMinutes() < 1;
	}
}
