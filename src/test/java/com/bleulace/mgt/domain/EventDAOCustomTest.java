package com.bleulace.mgt.domain;

import java.util.Date;

import junit.framework.Assert;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.crm.application.command.CreateAccountCommand;
import com.bleulace.mgt.application.command.AssignManagerCommand;
import com.bleulace.mgt.application.command.CreateEventCommand;

@Transactional
@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EventDAOCustomTest implements CommandGatewayAware
{
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private CreateAccountCommand createAccountCommand;

	@Autowired
	private CreateEventCommand createEventCommand;

	@Test
	public void testFindByAssignment()
	{
		gateway().send(createAccountCommand);
		gateway().send(createEventCommand);

		String accountId = createAccountCommand.getId();
		ManagementAssignment assignment = ManagementAssignment.MIX;

		AssignManagerCommand command = new AssignManagerCommand(
				createEventCommand.getId(), accountId, assignment);
		gateway().send(command);

		Assert.assertTrue(eventDAO.findByAssignment(accountId, assignment)
				.size() == 1);

		Assert.assertTrue(eventDAO.findByAssignment(accountId).size() == 1);
	}

	@Test
	public void testFindByDates()
	{
		gateway().send(createEventCommand);

		Date start = createEventCommand.getStart();
		Date end = createEventCommand.getEnd();
		Assert.assertEquals(1, eventDAO.findBetweenDates(start, end).size());

		end = LocalDateTime.fromDateFields(start).minusMinutes(15).toDate();
		Assert.assertEquals(0, eventDAO.findBetweenDates(start, end).size());
	}
}