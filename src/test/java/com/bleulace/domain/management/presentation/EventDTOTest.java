package com.bleulace.domain.management.presentation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.IntegrationTest;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.management.model.Event;
import com.bleulace.utils.Locator;
import com.bleulace.utils.dto.Mapper;

public class EventDTOTest implements IntegrationTest
{
	private Event event;

	@Autowired
	private ManagementCommandFactory factory;

	@Before
	public void setUp()
	{
		factory.createEvent();
		event = Locator.locate(Event.class);
	}

	@Test
	public void testBeanVal()
	{
		Mapper.map(event, EventDTO.class);
	}
}
