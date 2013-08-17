package com.bleulace.domain.management.presentation;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.management.model.Event;
import com.bleulace.utils.Locator;
import com.bleulace.utils.dto.Mapper;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;

public class EventDTOTest implements IntegrationTest, EventChangeListener
{
	private Event event;

	@Autowired
	private ManagementCommandFactory factory;

	@Autowired
	private Validator validator;

	private boolean eventCaught = false;

	@Before
	public void setUp()
	{
		factory.createEvent();
		event = Locator.locate(Event.class);
		eventCaught = false;
	}

	@Test
	public void testBeanVal()
	{
		for (ConstraintViolation<EventDTO> v : validator.validate(Mapper.map(
				event, EventDTO.class)))
		{
			Assert.fail(v.toString());
		}
	}

	@Test
	public void testEventNotification()
	{
		EventDTO dto = Mapper.map(event, EventDTO.class);
		dto.addEventChangeListener(this);
		dto.setCaption("foo");
		Assert.assertTrue(eventCaught);
	}

	@Override
	public void eventChange(EventChangeEvent eventChangeEvent)
	{
		eventCaught = true;
	}
}