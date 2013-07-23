package com.bleulace.mgt.presentation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Range;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.mgt.application.command.InviteGuestsCommand;
import com.bleulace.mgt.application.command.RsvpCommand;
import com.bleulace.mgt.domain.EventDAO;
import com.bleulace.utils.jpa.DateWindow;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduleStatusTest implements CommandGatewayAware
{
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private CreateAccountCommand createAccountCommand;

	@Autowired
	private InviteGuestsCommand inviteGuestsCommand;

	@Autowired
	private RsvpCommand rsvpCommand;

	@Test
	public void testIsAvailable()
	{
		gateway().send(createAccountCommand);
		assertSingleStatus(createAccountCommand.getId(), new Date(), DateTime
				.now().plusMinutes(15).toDate(), ScheduleStatus.AVAILABLE);
	}

	@Test
	public void testIsTentative()
	{
		gateway().send(inviteGuestsCommand);
		assertSingleStatus(inviteGuestsCommand.getGuestIds().iterator().next(),
				inviteGuestsCommand.getId(), ScheduleStatus.TENTATIVE);
	}

	@Test
	public void testIsBusy() throws Exception
	{
		gateway().send(rsvpCommand);
		Thread.sleep(250);
		assertSingleStatus(rsvpCommand.getGuestId(), rsvpCommand.getId(),
				ScheduleStatus.BUSY);
	}

	private void assertSingleStatus(String accountId, String eventId,
			ScheduleStatus status)
	{
		DateWindow window = eventDAO.findOne(eventId).getWindow();
		assertSingleStatus(accountId, window.getStart(), window.getEnd(),
				status);
	}

	private void assertSingleStatus(String accountId, Date start, Date end,
			ScheduleStatus status)
	{
		Set<ScheduleStatus> results = getStatuses(accountId, start, end);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals(status, results.iterator().next());
		Assert.assertEquals(status, ScheduleStatus.get(accountId, start, end));
	}

	private Set<ScheduleStatus> getStatuses(String accountId, Date start,
			Date end)
	{
		Set<ScheduleStatus> results = new HashSet<ScheduleStatus>();
		for (ScheduleStatus status : ScheduleStatus.values())
		{
			if (status.is(accountId, Range.between(start, end)))
			{
				results.add(status);
			}
		}
		return results;
	}
}
