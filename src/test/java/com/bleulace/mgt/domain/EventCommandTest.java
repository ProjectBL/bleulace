package com.bleulace.mgt.domain;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.CreateEventCommand;
import com.bleulace.mgt.application.command.InviteGuestsCommand;
import com.bleulace.mgt.application.command.MoveEventCommand;
import com.bleulace.mgt.application.command.ResizeEventCommand;
import com.bleulace.mgt.application.command.RsvpCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EventCommandTest implements CommandGatewayAware
{
	@Autowired
	private CreateEventCommand createEventCommand;

	@Autowired
	private MoveEventCommand moveEventCommand;

	@Autowired
	private ResizeEventCommand resizeEventCommand;

	@Autowired
	private InviteGuestsCommand inviteGuestsCommand;

	@Autowired
	private RsvpCommand rsvpCommand;

	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	protected void init()
	{
		repo = new SimpleJpaRepository<Event, String>(Event.class, em);
	}

	private JpaRepository<Event, String> repo;

	@Test
	public void testCreateEventCommand()
	{
		long count = repo.count();
		gateway().send(createEventCommand);
		Assert.assertEquals(count + 1, repo.count());
	}

	@Test
	public void moveEventCommand()
	{
		Date oldStart = repo.findOne(moveEventCommand.getId()).getWindow()
				.getStart();
		gateway().send(moveEventCommand);
		Date newStart = repo.findOne(moveEventCommand.getId()).getWindow()
				.getStart();
		Assert.assertTrue(newStart.after(oldStart));
	}

	@Test
	public void resizeEventCommand()
	{
		Event event = repo.findOne(resizeEventCommand.getId());

		Date oldStart = event.getWindow().getStart();
		Date oldEnd = event.getWindow().getEnd();

		gateway().send(resizeEventCommand);

		event = repo.findOne(resizeEventCommand.getId());

		Date newStart = event.getWindow().getStart();
		Date newEnd = event.getWindow().getEnd();

		Assert.assertTrue(newStart.after(oldStart));
		Assert.assertTrue(newEnd.after(oldEnd));
	}

	@Test
	public void testInviteGuestsCommand()
	{
		gateway().send(inviteGuestsCommand);
		Assert.assertNotNull(EventInvitation.find(inviteGuestsCommand
				.getGuestIds().iterator().next(), inviteGuestsCommand.getId()));
	}

	@Test
	public void testRsvpCommand() throws SchedulingConflictException
	{
		gateway().send(rsvpCommand);
		Assert.assertTrue(repo.findOne(rsvpCommand.getId()).getAttendees()
				.size() > 0);
	}
}