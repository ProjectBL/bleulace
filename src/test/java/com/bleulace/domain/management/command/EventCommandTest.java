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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.AuthenticatingTest;
import com.bleulace.domain.management.model.Event;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.utils.Locator;
import com.bleulace.utils.jpa.DateWindow;

@ActiveProfiles("test")
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
public class EventCommandTest extends AuthenticatingTest implements
		CommandGatewayAware
{
	@Autowired
	private ManagementCommandFactory factory;

	@Before
	public void createEvent()
	{
		factory.createEvent();
	}

	@Test
	public void createdEventExistsInDb()
	{
		Assert.assertNotNull(getEvent());
	}

	@Test
	public void testMoveEvent()
	{

		DateWindow oldDates = Locator.locate(Event.class).getWindow();

		final int minutes = 15;
		sendAndWait(new RescheduleEventCommand(getEvent().getId(),
				LocalDateTime.fromDateFields(oldDates.getStart())
						.plusMinutes(minutes).toDate()));

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

		sendAndWait(new RescheduleEventCommand(getEvent().getId(), newStart,
				newEnd));
		DateWindow newDates = Locator.locate(Event.class).getWindow();

		Assert.assertTrue(closeEnough(newStart, newDates.getStart()));
		Assert.assertTrue(closeEnough(newEnd, newDates.getEnd()));
	}

	@Test
	public void testRsvpCommand()
	{
		sendAndWait(new InviteGuestsCommand(getEvent().getId(), getAccount()
				.getId()));
		doRsvpAndAssertion(true);
		doRsvpAndAssertion(false);
	}

	private void doRsvpAndAssertion(boolean rsvp)
	{
		sendAndWait(new RsvpCommand(getEvent().getId(), getAccount().getId(),
				rsvp));
		assertRsvp(rsvp);
	}

	private void assertRsvp(boolean rsvp)
	{
		EventInvitee inv = Locator.locate(Event.class).getInvitees()
				.get(getAccount());
		Assert.assertNotNull(inv);
		RsvpStatus targetStatus = rsvp ? RsvpStatus.ACCEPTED
				: RsvpStatus.DECLINED;
		Assert.assertEquals(targetStatus, inv.getStatus());
	}

	public Event getEvent()
	{
		return Locator.locate(Event.class);
	}

	private boolean closeEnough(Date o1, Date o2)
	{
		return Period.fieldDifference(LocalDateTime.fromDateFields(o1),
				LocalDateTime.fromDateFields(o2)).getMinutes() < 1;
	}
}
