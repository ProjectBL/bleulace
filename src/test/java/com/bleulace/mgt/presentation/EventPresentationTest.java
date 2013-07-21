package com.bleulace.mgt.presentation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.mgt.application.command.RsvpCommand;

@ContextConfiguration("classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles("test")
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EventPresentationTest implements CommandGatewayAware
{
	@Autowired
	private RsvpCommand rsvpCommand;

	@Test
	public void testEventDTOMapping()
	{
		gateway().send(rsvpCommand);
		EventDTO dto = EventDTO.FINDER.findById(rsvpCommand.getId());
		Assert.assertNotNull(dto);
		Assert.assertNotNull(dto.getAttendees());
		Assert.assertNotNull(dto.getAssignments());
		Assert.assertEquals(rsvpCommand.getGuestId(), dto.getAttendees().get(0)
				.getId());
	}
}